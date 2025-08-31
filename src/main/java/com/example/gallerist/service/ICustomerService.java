package com.example.gallerist.service;

import com.example.gallerist.dto.DtoCustomer;
import com.example.gallerist.dto.DtoCustomerIU;

public interface ICustomerService {

    public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
}
