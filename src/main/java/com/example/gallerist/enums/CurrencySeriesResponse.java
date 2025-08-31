package com.example.gallerist.enums;

import lombok.Getter;

@Getter
public enum CurrencySeriesResponse {
    TL("TP_DK_TL_A"),
    USD("TP_DK_USD_A"),
    EUR("TP_DK_EUR_A"),
    GBP("TP_DK_GBP_A");

    private final String series;

    CurrencySeriesResponse(String series) {
        this.series = series;
    }
}
