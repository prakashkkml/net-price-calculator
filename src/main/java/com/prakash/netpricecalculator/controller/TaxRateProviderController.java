package com.prakash.netpricecalculator.controller;

import com.prakash.netpricecalculator.dto.TaxRateInput;
import com.prakash.netpricecalculator.dto.TaxRateResponse;
import com.prakash.netpricecalculator.exception.InvalidRequestException;
import com.prakash.netpricecalculator.model.TaxRate;
import com.prakash.netpricecalculator.service.TaxRateProviderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tax-rate-provider")
@RequiredArgsConstructor
@Validated
public class TaxRateProviderController {

    private final TaxRateProviderService taxRateProviderService;

    @GetMapping("/{countryCode}")
    @Operation(summary = "To fetch the tax rate based on the country code")
    public ResponseEntity<TaxRateResponse> getTaxRate(@PathVariable @NotBlank String countryCode) throws InvalidRequestException {
        Optional<TaxRate> optionalTaxRate = taxRateProviderService.get(countryCode);
        if (!optionalTaxRate.isPresent()) {
            throw new InvalidRequestException("The country code is not a valid one.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalTaxRate.get().getTaxRateResponse());
    }

    @GetMapping()
    @Operation(summary = "To fetch all the taxes for all the countries")
    public ResponseEntity<List<TaxRateResponse>> getAllTaxRates() {
        List<TaxRate> allTaxRates = taxRateProviderService.getAllTaxRates();
        List<TaxRateResponse> taxRateResponses = allTaxRates.stream().map(taxRate -> taxRate.getTaxRateResponse())
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(taxRateResponses);
    }

    @PutMapping()
    @Operation(summary = "To update the country vat(tax) value based on the country ISO code provided")
    public ResponseEntity<TaxRateResponse> update(@RequestBody TaxRateInput taxRateInput) throws InvalidRequestException {
        if (!taxRateProviderService.isValidCountry(taxRateInput.getCountryCode())) {
            throw new InvalidRequestException("The country code is not a valid one.");
        }
        TaxRate updatedTaxRate = taxRateProviderService.update(taxRateInput.getCountryCode(), taxRateInput.getVat());
        return ResponseEntity.status(HttpStatus.OK).body(updatedTaxRate.getTaxRateResponse());
    }
}
