package com.niew.demorestservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(ExchangeRateService.class)
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService service;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void testExchangeRateServiceSuccessfulRate()
            throws Exception {
        String json = "{\"base\":\"USD\",\"date\":\"2017-09-22\",\"rates\":{\"AUD\":1.255,\"BGN\":1.6351,\"BRL\":3.1318,\"CAD\":1.2269,\"CHF\":0.96882,\"CNY\":6.5885,\"CZK\":21.776,\"DKK\":6.2208,\"GBP\":0.73702,\"HKD\":7.8099,\"HRK\":6.2573,\"HUF\":258.95,\"IDR\":13300.0,\"ILS\":3.4909,\"INR\":64.795,\"JPY\":112.04,\"KRW\":1131.0,\"MXN\":17.8,\"MYR\":4.19,\"NOK\":7.7914,\"NZD\":1.3658,\"PHP\":50.608,\"PLN\":3.5676,\"RON\":3.8431,\"RUB\":57.594,\"SEK\":7.9724,\"SGD\":1.3455,\"THB\":33.09,\"TRY\":3.495,\"ZAR\":13.24,\"EUR\":0.83605}}";
        this.server.expect(requestTo("http://api.fixer.io/latest?base=USD"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        BigDecimal rate = this.service.getExchangeRate("USD", "EUR");
        assertEquals(new BigDecimal("0.83605"), rate);
    }

}
