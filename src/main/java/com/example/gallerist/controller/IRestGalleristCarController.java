package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoGalleristCar;
import com.example.gallerist.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {

    public RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
}
