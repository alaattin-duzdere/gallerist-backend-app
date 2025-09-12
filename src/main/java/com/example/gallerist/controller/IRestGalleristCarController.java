package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoGalleristCar;
import com.example.gallerist.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {

    public RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);

    public RootEntity<DtoGalleristCar> getGalleristCarById(Long id);

    public RootEntity<DtoGalleristCar> updateGalleristCar(Long id, DtoGalleristCarIU dtoGalleristCarIU);
}
