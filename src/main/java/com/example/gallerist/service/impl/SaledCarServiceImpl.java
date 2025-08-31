package com.example.gallerist.service.impl;

import com.example.gallerist.dto.*;
import com.example.gallerist.enums.CarStatusType;
import com.example.gallerist.enums.CurrencyType;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Car;
import com.example.gallerist.model.Customer;
import com.example.gallerist.model.SaledCar;
import com.example.gallerist.repository.CarRepository;
import com.example.gallerist.repository.CustomerRepository;
import com.example.gallerist.repository.GalleristRepository;
import com.example.gallerist.repository.SaledCarRepository;
import com.example.gallerist.service.ICurrencyRatesService;
import com.example.gallerist.service.ISaledCarService;
import com.example.gallerist.utils.CurrencyUtils;
import com.example.gallerist.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SaledCarServiceImpl implements ISaledCarService {

    private final CustomerRepository customerRepository;

    private final CarRepository carRepository;

    private final ICurrencyRatesService currencyRatesService;

    private final GalleristRepository galleristRepository;

    private final SaledCarRepository saledCarRepository;

    public SaledCarServiceImpl(CustomerRepository customerRepository, CarRepository carRepository, ICurrencyRatesService currencyRatesService, GalleristRepository galleristRepository, SaledCarRepository saledCarRepository) {
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
        this.currencyRatesService = currencyRatesService;
        this.galleristRepository = galleristRepository;
        this.saledCarRepository = saledCarRepository;
    }

    public BigDecimal convertToTL(BigDecimal amount, CurrencyType from) {
        if (from == CurrencyType.TL) {return amount;}
        // Get currency rates from external service
        CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRates(DateUtils.getCurrrentDate(), DateUtils.getCurrrentDate(), from);
        Map<String, String> rates = currencyRatesResponse.getItems().get(0).getRates();
        String rate = rates.get(CurrencyUtils.getCurrencySeriesResponse(from).getSeries());
        log.info("GetItems : {}" ,currencyRatesResponse.getItems().get(0).getRates());
        log.info("amount: {}", amount);
        log.info("from:  {}", from);
        log.info("Rate: {}", rate);
        // Convert amount to TL using the fetched rate
        try{
            return amount.multiply(new BigDecimal(rate));
        }catch (NullPointerException e){
            throw new BaseException(new ErrorMessage(MessageType.Currency_Rates_Is_Occured, e.getMessage()));
        }
    }

//    public BigDecimal convertCustomerAmountToUSD(Customer customer) {
//        CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRates(DateUtils.getCurrrentDate(new Date()), DateUtils.getCurrrentDate(new Date()), CurrencySeries.USD);
//        BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().getFirst().getUsd());
//        BigDecimal customerUsdAmount = customer.getAccount().getAmount().divide(usd, 2, RoundingMode.HALF_UP);
//        return customerUsdAmount;
//    }

    public boolean checkCarStatus(DtoSaledCarIU dtoSaledCarIU){
        Optional<Car> optCar = carRepository.findById(dtoSaledCarIU.getCarId());
        return optCar.isEmpty() || optCar.get().getCarStatusType() != CarStatusType.SALED;
    }

    public BigDecimal remainingCustomerAmount(Customer customer, Car car) {
        BigDecimal customerTLAmount = convertToTL(customer.getAccount().getAmount(),customer.getAccount().getCurrencyType()); // Convert customer's amount to TL
        log.info("customerTLAmount: {}", customerTLAmount);
        BigDecimal remainingCustomerTlAmount = customerTLAmount.subtract(convertToTL(car.getPrice(), car.getCurrencyType())); // Remaining amount in TL after buying the car
        log.info("remainingCustomerTlAmount: {}", remainingCustomerTlAmount);
        if (customer.getAccount().getCurrencyType() == CurrencyType.TL){
            return remainingCustomerTlAmount;
        }
        // Convert remaining amount back to customer's original currency
        CurrencyRatesResponse currencyRatesResponse = currencyRatesService.getCurrencyRates(DateUtils.getCurrrentDate(), DateUtils.getCurrrentDate(), customer.getAccount().getCurrencyType());
        Map<String, String> rates = currencyRatesResponse.getItems().get(0).getRates();
        String rate = rates.get(CurrencyUtils.getCurrencySeriesResponse(customer.getAccount().getCurrencyType()).getSeries());

        return remainingCustomerTlAmount.divide(new BigDecimal(rate), 2, RoundingMode.HALF_UP);
    }

    public boolean checkAmount(DtoSaledCarIU dtoSaledCarIU){
        Optional<Customer> optCustomer = customerRepository.findById(dtoSaledCarIU.getCustomerId());
        Optional<Car> optCar = carRepository.findById(dtoSaledCarIU.getCarId());
        if(optCustomer.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoSaledCarIU.getCustomerId().toString()));
        }
        if(optCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoSaledCarIU.getCarId().toString()));
        }
        BigDecimal carTlPrice = convertToTL(optCar.get().getPrice(), optCar.get().getCurrencyType());
        BigDecimal customerTlAmount = convertToTL(optCustomer.get().getAccount().getAmount(), optCustomer.get().getAccount().getCurrencyType());
        return customerTlAmount.compareTo(carTlPrice) >= 0;
    }

    private SaledCar createSaledCar(DtoSaledCarIU dtoSaledCarIU){
        SaledCar saledCar = new SaledCar();
        saledCar.setCreateTime(new Date());

        saledCar.setCustomer(customerRepository.findById(dtoSaledCarIU.getCustomerId()).orElse(null));
        saledCar.setGallerist(galleristRepository.findById(dtoSaledCarIU.getGalleristId()).orElse(null));
        saledCar.setCar(carRepository.findById(dtoSaledCarIU.getCarId()).orElse(null));

        return saledCar;
    }

    @Override
    public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU) {
        if(!checkCarStatus(dtoSaledCarIU)){
            throw new BaseException(new ErrorMessage(MessageType.Car_Status_Is_Already_Saled,dtoSaledCarIU.getCarId().toString()));
        }

        if(!checkAmount(dtoSaledCarIU)){
            throw new BaseException(new ErrorMessage(MessageType.Customer_Amount_Is_Not_Enough,dtoSaledCarIU.getCustomerId().toString()));
        }

        SaledCar savedSaledCar = saledCarRepository.save(createSaledCar(dtoSaledCarIU));

        // Update car status to SALED
        Car car = savedSaledCar.getCar();
        car.setCarStatusType(CarStatusType.SALED);
        carRepository.save(car);

        // Update customer's account amount
        Customer customer = savedSaledCar.getCustomer();
        customer.getAccount().setAmount(remainingCustomerAmount(customer,car));
        customerRepository.save(customer);

        return toDto(savedSaledCar);
    }

    public DtoSaledCar toDto(SaledCar saledCar){
        DtoSaledCar dtoSaledCar = new DtoSaledCar();
        DtoCustomer dtoCustomer = new DtoCustomer();
        DtoGallerist dtoGallerist = new DtoGallerist();
        DtoCar dtoCar = new DtoCar();

        BeanUtils.copyProperties(saledCar,dtoSaledCar);
        BeanUtils.copyProperties(saledCar.getCustomer(),dtoCustomer);
        BeanUtils.copyProperties(saledCar.getCar(),dtoCar);
        BeanUtils.copyProperties(saledCar.getGallerist(),dtoGallerist);

        dtoSaledCar.setCustomer(dtoCustomer);
        dtoSaledCar.setCar(dtoCar);
        dtoSaledCar.setGallerist(dtoGallerist);

        return dtoSaledCar;
    }
}
