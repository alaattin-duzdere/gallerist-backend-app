package com.example.gallerist.service;

import com.example.gallerist.dto.CurrencyRatesResponse;
import com.example.gallerist.enums.CurrencyType;

public interface ICurrencyRatesService {

    public CurrencyRatesResponse getCurrencyRates(String startDate, String endDate, CurrencyType currencyType);
}
