package com.example.gallerist.controller;

import com.example.gallerist.dto.CurrencyRatesResponse;
import com.example.gallerist.enums.CurrencyType;

public interface IRestCurrencyController {

    public RootEntity<CurrencyRatesResponse> getCurrencyRates(String startDate, String endDate, CurrencyType currencyType);
}
