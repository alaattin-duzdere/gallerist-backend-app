package com.example.gallerist.service;

import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoAdressIU;

public interface IAdressService {

    public DtoAdress saveAdress(DtoAdressIU dtoAdressUI);
}
