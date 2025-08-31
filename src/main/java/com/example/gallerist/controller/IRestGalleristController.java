package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoGallerist;
import com.example.gallerist.dto.DtoGalleristIU;

public interface IRestGalleristController {

    public RootEntity<DtoGallerist> saveGallerist(DtoGalleristIU dtoGalleristIU);
}
