package com.niew.demorestservice.service;

import java.math.BigDecimal;
/**
 * Current exchange rate retrieval service
 */
public interface ExchangeRateService {
    /**
     * Returns current exchange rate for a pair of currencies (currencyFrom, currencyTo)
     * @param currencyFrom - source currency
     * @param currencyTo - dest currency
     * @return - exchange rate
     */
    BigDecimal getExchangeRate(String currencyFrom, String currencyTo);
}
