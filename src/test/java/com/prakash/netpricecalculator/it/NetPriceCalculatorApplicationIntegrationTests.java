package com.prakash.netpricecalculator.it;

import com.prakash.netpricecalculator.dto.TaxRateResponse;
import com.prakash.netpricecalculator.it.model.TaxRateTestModel;
import com.prakash.netpricecalculator.model.NetPriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class NetPriceCalculatorApplicationIntegrationTests {
    private static final String COUNTRY_CODE = "DE";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testNetPriceCalculatorWithValidGrossPriceAndCountryIso() {
        // arrange
        List<TaxRateTestModel> testDataValues = generateTaxRateTestData();

        // act and assert
        for (TaxRateTestModel testModel : testDataValues) {
            updateVatRate(testModel.getVatRate());
            ResponseEntity<NetPriceResponse> response = restTemplate.getForEntity("/api/v1/net-price-calculator?grossPrice="
                    + testModel.getGrossPrice() + "&countryISO=" + COUNTRY_CODE, NetPriceResponse.class);
            assertTrue(response.getStatusCode().is2xxSuccessful());
            BigDecimal actualNetPrice = response.getBody().getNetPrice();
            assertThat(actualNetPrice).isEqualTo(testModel.getNetPrice());
        }
    }

    @Test
    public void testNetPriceCalculatorWithInValidGrossPriceAndCountryIso() {
        // arrange
        BigDecimal grossPrice = BigDecimal.valueOf(-10);
        updateVatRate(BigDecimal.valueOf(0.5));

        // act
        ResponseEntity<NetPriceResponse> response = restTemplate.getForEntity("/api/v1/net-price-calculator?grossPrice="
                + grossPrice + "&countryISO=" + COUNTRY_CODE, NetPriceResponse.class);

        // assert
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    private static List<TaxRateTestModel> generateTaxRateTestData() {
        List<TaxRateTestModel> testDataValues = new ArrayList<>();
        testDataValues.add(new TaxRateTestModel(BigDecimal.valueOf(100.00), BigDecimal.valueOf(0.19), BigDecimal.valueOf(81.0)));
        testDataValues.add(new TaxRateTestModel(BigDecimal.valueOf(1.99), BigDecimal.valueOf(0.20), BigDecimal.valueOf(1.6)));
        testDataValues.add(new TaxRateTestModel(BigDecimal.valueOf(100.00), BigDecimal.valueOf(0.5), BigDecimal.valueOf(50.0)));
        return testDataValues;
    }

    private void updateVatRate(BigDecimal vatRate) {
        String taxRateInput = "{ \"countryCode\": \"DE\", \"vat\": " + vatRate + " }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(taxRateInput, headers);
        ResponseEntity<TaxRateResponse> response = restTemplate.exchange("/api/v1/tax-rate-provider", HttpMethod.PUT, entity, TaxRateResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}