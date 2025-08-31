package com.example.gallerist.enums;

import lombok.Getter;

@Getter
public enum CurrencySeriesRequest {
    TL("TP.DK.TL.A"),
    USD("TP.DK.USD.A"),
    EUR("TP.DK.EUR.A"),
    GBP("TP.DK.GBP.A");

    private final String series;

    CurrencySeriesRequest(String series) {
        this.series = series;
    }
}
