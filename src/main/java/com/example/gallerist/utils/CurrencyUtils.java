package com.example.gallerist.utils;

import com.example.gallerist.enums.CurrencySeriesRequest;
import com.example.gallerist.enums.CurrencySeriesResponse;
import com.example.gallerist.enums.CurrencyType;

public class CurrencyUtils {

    public static CurrencySeriesRequest getCurrencySeriesRequest(CurrencyType currencyType) { // helper method
        return switch (currencyType) {
            case TL -> CurrencySeriesRequest.TL;
            case USD -> CurrencySeriesRequest.USD;
            case EUR -> CurrencySeriesRequest.EUR;
            case GBP -> CurrencySeriesRequest.GBP;
        };
    }

    public static CurrencySeriesResponse getCurrencySeriesResponse(CurrencyType currencyType) { // helper method
        return switch (currencyType) {
            case TL -> CurrencySeriesResponse.TL;
            case USD -> CurrencySeriesResponse.USD;
            case EUR -> CurrencySeriesResponse.EUR;
            case GBP -> CurrencySeriesResponse.GBP;
        };
    }


}
