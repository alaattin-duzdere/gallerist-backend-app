package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoCar;
import com.example.gallerist.dto.DtoCarIU;

public interface IRestCarController {
    public RootEntity<DtoCar> saveCar(DtoCarIU dtoCarIU);
}
