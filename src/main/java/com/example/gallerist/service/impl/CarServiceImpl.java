package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoCar;
import com.example.gallerist.dto.DtoCarIU;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Car;
import com.example.gallerist.repository.CarRepository;
import com.example.gallerist.service.ICarService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CarServiceImpl implements ICarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(DtoCarIU dtoCarIU){
        Car car = new Car();
        BeanUtils.copyProperties(dtoCarIU, car);
        car.setCreateTime(new Date());
        return car;
    }

    @Override
    public DtoCar saveCar(DtoCarIU dtoCarIU) {
        Car savedCar = carRepository.save(createCar(dtoCarIU));

        DtoCar dtoCar = new DtoCar();
        BeanUtils.copyProperties(savedCar, dtoCar);
        return dtoCar;
    }

    @Override
    public DtoCar getCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No car found with id: " + id)));
        DtoCar dtoCar = new DtoCar();
        BeanUtils.copyProperties(car, dtoCar);
        return dtoCar;
    }

    @Override
    public DtoCar updateCar(Long id, DtoCarIU dtoCarIU) {
        Car car = carRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No car found with id: " + id)));
        BeanUtils.copyProperties(dtoCarIU, car, "id", "createTime");
        Car savedCar = carRepository.save(car);

        DtoCar dtoCar = new DtoCar();
        BeanUtils.copyProperties(savedCar, dtoCar);
        return dtoCar;
    }
}
