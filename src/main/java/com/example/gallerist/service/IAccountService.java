package com.example.gallerist.service;

import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAccountIU;
import com.example.gallerist.dto.DtoAdressIU;

public interface IAccountService {

    public DtoAccount saveAccount(DtoAccountIU dtoAccountIU);

    public DtoAccount getAdressById(Long id);

    public DtoAccount updateAccount(Long id, DtoAccountIU dtoAccountIU);
}
