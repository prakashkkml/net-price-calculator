package com.prakash.netpricecalculator.service;

import com.prakash.netpricecalculator.model.TaxRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaxRateProviderService {

    private static final Map<String, TaxRate> taxRateProvider = new ConcurrentHashMap<>();
    @Value("${country.default.vat.rate}")
    private BigDecimal defaultVatRate;

    /**
     * This method initializes countries with the default-vat value in the taxRateProvider in-memory cache.
     * The defaultVatRate is configurable and can be modified by calling update API.
     */
    @PostConstruct
    private void initializeTaxRateProvider() {
        for (String iso : Locale.getISOCountries()) {
            create(new Locale("", iso).getDisplayCountry(), iso, defaultVatRate);
        }
        log.info("The Tax Rate provider has been initialized with the countries and with the default vat rate of {}", defaultVatRate);
    }

    /**
     * This method validates the provided countryCode is valid or not.
     *
     * @param countryCode
     * @return
     */
    public boolean isValidCountry(String countryCode) {
        return taxRateProvider.containsKey(countryCode);
    }

    /**
     * This method returns Optional.empty() if the countryCode is invalid otherwise
     * VAT-rate for the country is returned.
     *
     * @param countryCode
     * @return Optional<TaxRate>
     */
    public Optional<TaxRate> get(String countryCode) {
        return Optional.ofNullable(taxRateProvider.get(countryCode));
    }

    /**
     * This method returns a list tax rates for all the countries.
     *
     * @return
     */
    public List<TaxRate> getAllTaxRates() {
        return taxRateProvider.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    /**
     * This method allows the user to update the country tax by providing a valid countryCode
     *
     * @param countryCode
     * @param updatedVat
     * @return TaxRate
     */
    public TaxRate update(String countryCode, BigDecimal updatedVat) {
        TaxRate taxRateUpdate = taxRateProvider.get(countryCode);
        taxRateUpdate.setVat(updatedVat);
        return taxRateUpdate;
    }

    private TaxRate create(String countryName, String countryISOCode, BigDecimal defaultVatRate) {
        return taxRateProvider.put(countryISOCode, TaxRate.builder().country(countryName).countryCode(countryISOCode).vat(defaultVatRate).build());
    }

}
