package com.prakash.netpricecalculator.model;

import com.prakash.netpricecalculator.dto.TaxRateResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TaxRate {
    private String country;
    private String countryCode;
    private BigDecimal vat;

    public TaxRateResponse getTaxRateResponse() {
        return TaxRateResponse.builder()
                .country(country)
                .countryCode(countryCode)
                .vat(vat)
                .build();
    }
}
