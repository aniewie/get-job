package com.niew.demorestservice.service;

import org.springframework.data.util.Pair;

import java.math.BigDecimal;

public interface ExchangeRateService {
    Pair<BigDecimal, String> getExchangeRate(String currencyFrom, String currencyTo);
}
