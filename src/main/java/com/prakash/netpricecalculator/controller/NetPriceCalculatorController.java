package com.prakash.netpricecalculator.controller;

import com.prakash.netpricecalculator.model.NetPriceResponse;
import com.prakash.netpricecalculator.service.NetPriceCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/net-price-calculator")
@RequiredArgsConstructor
@Validated
public class NetPriceCalculatorController {

    private final NetPriceCalculatorService netPriceCalculatorService;

    @GetMapping
    @Operation(summary = "To perform net-price calculation based on the gross-price and country ISO code")
    public ResponseEntity<NetPriceResponse> calculateNetPrice(@RequestParam @Min(0) BigDecimal grossPrice, @RequestParam @NotBlank String countryISO) {
        NetPriceResponse netPriceResponse = netPriceCalculatorService.calculateNetPrice(grossPrice, countryISO);
        return ResponseEntity.status(HttpStatus.OK).body(netPriceResponse);
    }

}
