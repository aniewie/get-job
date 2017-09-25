package com.niew.demorestservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niew.demorestservice.exception.ExchangeRateNotFoundException;
import com.niew.demorestservice.exception.ExchangeRateServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
/**
 * Current exchange rate retrieval service
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private final RestTemplate restTemplate;

    public ExchangeRateServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Returns current exchange rate for a pair of currencies (currencyFrom, currencyTo)
     * Calls http://api.fixer.io/latest
     * @see ExchangeRateService
     * */
    @Override
    public BigDecimal getExchangeRate(String currencyFrom, String currencyTo) {
        BigDecimal resultRate = BigDecimal.ONE;
        if (!currencyFrom.equals(currencyTo)) {
            try {
                ResponseEntity<String> response = this.restTemplate.getForEntity("http://api.fixer.io/latest?base={from}", String.class, currencyFrom);
                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new ExchangeRateServiceException(response.getStatusCode() + response.getBody());
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode value = root.path("rates").get(currencyTo);
                if (value == null) {
                    throw new ExchangeRateNotFoundException("No currency rate for: " + currencyTo);
                }
                resultRate = value.decimalValue();
            } catch (ExchangeRateNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ExchangeRateServiceException(e.getMessage());
            }

        }
        return resultRate;
    }

}
