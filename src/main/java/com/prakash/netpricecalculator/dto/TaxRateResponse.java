package com.prakash.netpricecalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxRateResponse {
    private String country;
    private String countryCode;
    private BigDecimal vat;
}
