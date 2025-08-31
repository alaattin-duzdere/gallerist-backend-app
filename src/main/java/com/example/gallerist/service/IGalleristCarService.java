package com.example.gallerist.service;

import com.example.gallerist.dto.DtoGallerist;
import com.example.gallerist.dto.DtoGalleristCar;
import com.example.gallerist.dto.DtoGalleristCarIU;

public interface IGalleristCarService {

    public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
}
