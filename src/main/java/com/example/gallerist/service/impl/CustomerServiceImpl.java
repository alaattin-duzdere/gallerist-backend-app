package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoCustomer;
import com.example.gallerist.dto.DtoCustomerIU;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Account;
import com.example.gallerist.model.Adress;
import com.example.gallerist.model.Customer;
import com.example.gallerist.repository.AccountRepository;
import com.example.gallerist.repository.AdressRepository;
import com.example.gallerist.repository.CustomerRepository;
import com.example.gallerist.service.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final AdressRepository adressRepository;
    private final AccountRepository accountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AdressRepository adressRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.adressRepository = adressRepository;
        this.accountRepository = accountRepository;
    }

    private Customer createCustomer(DtoCustomerIU dtoCustomerIU) {
        // Control if the Adress and Account exist
        Optional<Adress> optAdress = adressRepository.findById(dtoCustomerIU.getAdressId());
        if (optAdress.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoCustomerIU.getAdressId().toString()));
        }

        Optional<Account> optAccount = accountRepository.findById(dtoCustomerIU.getAccountId());
        if (optAccount.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist, dtoCustomerIU.getAccountId().toString()));
        }

        Customer customer = new Customer();
        customer.setCreateTime(new Date());
        BeanUtils.copyProperties(dtoCustomerIU, customer);
        customer.setAccount(optAccount.get());
        customer.setAdress(optAdress.get());
        return customer;
    }

    @Override
    public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
        Customer savedCustomer = customerRepository.save(createCustomer(dtoCustomerIU));

        DtoCustomer dtoCustomer = new DtoCustomer();
        BeanUtils.copyProperties(savedCustomer, dtoCustomer); // the Adress and Account will not be copied here
                                                              // you need to convert them separately
        // Convert the Adress and Account to their respective DTOs
        DtoAccount dtoAccount = new DtoAccount();
        BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(savedCustomer.getAdress(), dtoAdress);

        dtoCustomer.setAccount(dtoAccount);
        dtoCustomer.setAdress(dtoAdress);

        return dtoCustomer;
    }
}
