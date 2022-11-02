package com.prakash.netpricecalculator.service;

import com.prakash.netpricecalculator.config.ConfigProperties;
import com.prakash.netpricecalculator.dto.TaxRateResponse;
import com.prakash.netpricecalculator.model.NetPriceResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class NetPriceCalculatorService {

    private static final String COUNTRY_VAT_CACHE = "countryVatCache";
    private static final String TAX_RATE_PROVIDER_URL = "/api/v1/tax-rate-provider/";
    private final RestTemplate restTemplate;

    private final ConfigProperties properties;

    /**
     * This method calculates the net price based on the input gross-price and countryISO code
     *
     * @param grossPrice
     * @param countryISO
     * @return NetPriceResponse
     */
    public NetPriceResponse calculateNetPrice(BigDecimal grossPrice, String countryISO) {
        BigDecimal vat = getCountryVatByCountryIso(countryISO);
        BigDecimal vatAmount = grossPrice.multiply(vat);
        BigDecimal netPrice = grossPrice.subtract(vatAmount).setScale(1, RoundingMode.HALF_UP);
        return NetPriceResponse.builder()
                .countryCode(countryISO)
                .grossPrice(grossPrice)
                .vatRate(vat)
                .netPrice(netPrice).build();
    }

    /**
     * This method retrieves the country VAT value from TaxRateProvider and also simulates the microservice behaviour.
     *
     * @param countryISO
     * @return vat value
     */
    @Cacheable(value = COUNTRY_VAT_CACHE, keyGenerator = "countryVatKeyGenerator")
    private BigDecimal getCountryVatByCountryIso(String countryISO) {
        ResponseEntity<TaxRateResponse> response = restTemplate.getForEntity(getTaxProviderEndPoint(countryISO), TaxRateResponse.class);
        log.debug("received tax rate for country code {} with VAT as {}", countryISO, response.getBody().getVat());
        return response.getBody().getVat();
    }

    private String getTaxProviderEndPoint(String countryISO) {
        return new StringBuilder(properties.getUrl()).append(TAX_RATE_PROVIDER_URL).append(countryISO).toString();
    }
}
