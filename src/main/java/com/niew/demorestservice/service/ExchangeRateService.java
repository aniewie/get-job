package com.niew.demorestservice.service;

import java.math.BigDecimal;

public interface ExchangeRateService {
    BigDecimal getExchangeRate(String currencyFrom, String currencyTo);
}
