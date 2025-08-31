package com.example.gallerist.service;

import com.example.gallerist.dto.DtoSaledCar;
import com.example.gallerist.dto.DtoSaledCarIU;

public interface ISaledCarService {

    public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU);
}
