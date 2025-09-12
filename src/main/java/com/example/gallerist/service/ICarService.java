package com.example.gallerist.service;

import com.example.gallerist.dto.DtoCar;
import com.example.gallerist.dto.DtoCarIU;

public interface ICarService{

    public DtoCar saveCar(DtoCarIU dtoCarIU);

    public DtoCar getCarById(Long id);

    public DtoCar updateCar(Long id, DtoCarIU dtoCarIU);
}
