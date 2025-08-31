package com.example.gallerist.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CurrencyRatesItems {

    @JsonAnySetter
    public void setRates(String key, String value) {
        this.rates.put(key, value);
    }

    @JsonProperty("Tarih")
    private String date;

    private Map<String, String> rates = new HashMap<>();

    @JsonProperty("UNIXTIME")
    private Map<String,String> unixTime;
}



//    @JsonProperty("TP_DK_USD_A")
//    private String rates;