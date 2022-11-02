package com.prakash.netpricecalculator.service;

import com.prakash.netpricecalculator.config.ConfigProperties;
import com.prakash.netpricecalculator.dto.TaxRateResponse;
import com.prakash.netpricecalculator.model.NetPriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class NetPriceCalculatorServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    ConfigProperties configProperties;

    @InjectMocks
    NetPriceCalculatorService netPriceCalculatorService;

    @Test
    public void testCalculateNetPrice() {
        // arrange
        String expectedCountryCode = "DE";
        BigDecimal expectedGrossPrice = BigDecimal.valueOf(100);
        BigDecimal expectedVatRate = BigDecimal.valueOf(0.19);
        BigDecimal expectedNetPrice = BigDecimal.valueOf(81.0);

        TaxRateResponse germanyTaxResponseRate = TaxRateResponse.builder()
                .vat(expectedVatRate)
                .countryCode(expectedCountryCode)
                .country("Germany")
                .build();

        Mockito.when(configProperties.getUrl()).thenReturn("http://localhost:8080");
        Mockito.when(restTemplate.getForEntity(anyString(), eq(TaxRateResponse.class))).thenReturn(new ResponseEntity(germanyTaxResponseRate, HttpStatus.OK));

        // act
        NetPriceResponse netPriceResponse = netPriceCalculatorService.calculateNetPrice(expectedGrossPrice, expectedCountryCode);

        // assert
        assertNotNull(netPriceResponse);
        assertEquals(expectedCountryCode, netPriceResponse.getCountryCode());
        assertEquals(expectedGrossPrice, netPriceResponse.getGrossPrice());
        assertEquals(expectedVatRate, netPriceResponse.getVatRate());
        assertEquals(expectedNetPrice, netPriceResponse.getNetPrice());
    }

    @Test
    public void testCalculateNetPriceWithSmallerGrossPrice() {
        // arrange
        String expectedCountryCode = "DE";
        BigDecimal expectedGrossPrice = BigDecimal.valueOf(1.99);
        BigDecimal expectedVatRate = BigDecimal.valueOf(0.2);
        BigDecimal expectedNetPrice = BigDecimal.valueOf(1.6);

        TaxRateResponse germanyTaxResponseRate = TaxRateResponse.builder()
                .vat(expectedVatRate)
                .countryCode(expectedCountryCode)
                .country("Germany")
                .build();

        Mockito.when(configProperties.getUrl()).thenReturn("http://localhost:8080");
        Mockito.when(restTemplate.getForEntity(anyString(), eq(TaxRateResponse.class))).thenReturn(new ResponseEntity(germanyTaxResponseRate, HttpStatus.OK));

        // act
        NetPriceResponse netPriceResponse = netPriceCalculatorService.calculateNetPrice(expectedGrossPrice, expectedCountryCode);

        // assert
        assertNotNull(netPriceResponse);
        assertEquals(expectedCountryCode, netPriceResponse.getCountryCode());
        assertEquals(expectedGrossPrice, netPriceResponse.getGrossPrice());
        assertEquals(expectedVatRate, netPriceResponse.getVatRate());
        assertEquals(expectedNetPrice, netPriceResponse.getNetPrice());
    }

}