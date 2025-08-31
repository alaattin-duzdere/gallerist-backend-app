package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoSaledCar;
import com.example.gallerist.dto.DtoSaledCarIU;

public interface IRestSaledCarController {

    public RootEntity<DtoSaledCar> buyCar(DtoSaledCarIU dtoSaledCarIU);
}
