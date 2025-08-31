package com.example.gallerist.service.impl;

import com.example.gallerist.dto.CurrencyRatesResponse;
import com.example.gallerist.enums.CurrencyType;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.service.ICurrencyRatesService;
import com.example.gallerist.utils.CurrencyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CurrencyRatesServiceImpl implements ICurrencyRatesService {

    //https://evds2.tcmb.gov.tr/service/evds/series=TP.DK.USD.A&startDate=26-08-2025&endDate=26-08-2025&type=json

    @Override
    public CurrencyRatesResponse getCurrencyRates(String startDate, String endDate, CurrencyType currencyType) {
        String rootUrl = "https://evds2.tcmb.gov.tr/service/evds/";

        String series = CurrencyUtils.getCurrencySeriesRequest(currencyType).getSeries();
        String type = "json";

        String endPoint = rootUrl+"series="+series+"&startDate="+startDate+"&endDate="+endDate+"&type="+type ;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key","rnnSBI8etS");

        HttpEntity<?> httpEntity= new HttpEntity<>(httpHeaders);
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CurrencyRatesResponse> response = restTemplate.exchange(endPoint, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<CurrencyRatesResponse>(){});
            log.info("Exchange method is succesfull. Currency Rates Response: {}", response);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }

        } catch (Exception e) {
            log.info("getCurrencyRates is failed: {}", e.getMessage());
            throw new BaseException(new ErrorMessage(MessageType.Currency_Rates_Is_Occured,e.getMessage()));
        }
        return null;
    }
}
