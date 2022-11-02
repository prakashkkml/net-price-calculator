package com.prakash.netpricecalculator.it.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TaxRateTestModel {
    private BigDecimal grossPrice, vatRate, netPrice;
}
