package com.prakash.netpricecalculator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TaxRateInput {
    @NotBlank
    private String countryCode;
    @NotNull
    private BigDecimal vat;
}
