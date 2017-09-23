package com.niew.demorestservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private final RestTemplate restTemplate;

    public ExchangeRateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Pair<BigDecimal, String> getExchangeRate(String currencyFrom, String currencyTo) {
        BigDecimal resultRate = BigDecimal.ONE;
        String resultCurrency = currencyTo;
        if (!currencyFrom.equals(currencyTo)) {
            try {
                ResponseEntity<String> response = this.restTemplate.getForEntity("http://api.fixer.io/latest?base={from}", String.class, currencyFrom);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode value = root.path("rates").get(currencyTo);
                resultRate = value.decimalValue();
            } catch (Exception e) {
                logger.warn(new StringBuilder("Error while retrieving currency exchange for ").append(currencyFrom).append(" ").append(currencyTo).toString(), e);
                resultCurrency = currencyFrom;
            }
        }
        return Pair.of(resultRate, resultCurrency);
    }

}
