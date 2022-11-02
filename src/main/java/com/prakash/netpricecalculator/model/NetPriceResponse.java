package com.prakash.netpricecalculator.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class NetPriceResponse {
    private String countryCode;
    private BigDecimal grossPrice;
    private BigDecimal vatRate;
    private BigDecimal netPrice;
}
