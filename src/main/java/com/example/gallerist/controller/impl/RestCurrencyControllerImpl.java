package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestCurrencyController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.CurrencyRatesResponse;
import com.example.gallerist.enums.CurrencyType;
import com.example.gallerist.service.ICurrencyRatesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/")
public class RestCurrencyControllerImpl extends RestBaseController implements IRestCurrencyController {

    private final ICurrencyRatesService currencyRatesService;

    public RestCurrencyControllerImpl(ICurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @GetMapping("/currency-rates")
    @Override
    public RootEntity<CurrencyRatesResponse> getCurrencyRates(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("currencyType") CurrencyType currencyType) {
            return ok(currencyRatesService.getCurrencyRates(startDate, endDate, currencyType));
    }
}
