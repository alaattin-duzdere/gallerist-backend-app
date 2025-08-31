package com.example.gallerist.controller;

import com.example.gallerist.dto.DtoAccount;
import com.example.gallerist.dto.DtoAccountIU;
import jakarta.persistence.criteria.Root;

public interface IRestAccountController {
    public RootEntity<DtoAccount> saveAccount(DtoAccountIU dtoAccountIU);
}
