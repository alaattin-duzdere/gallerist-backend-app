package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoCar;
import com.example.gallerist.dto.DtoCarIU;
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
}
