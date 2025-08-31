package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoAdressIU;

public interface IRestAdressController{

    RootEntity<DtoAdress> saveAdress(DtoAdressIU dtoAdressIU);
}
