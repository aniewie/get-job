package com.niew.demorestservice;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;
import com.niew.demorestservice.service.ExchangeRateService;
import com.niew.demorestservice.service.ProductPackageService;
import com.niew.demorestservice.service.ProductPackageServiceImpl;
import com.niew.demorestservice.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class ProductPackageServiceTest {

    private static final Product PRODUCT_XXX =  new Product(1, "XXX", "Shoes", 123L);
    private static final Product PRODUCT_YYY = new Product(5, "YYY", "Lipstick", 12L);
    private static final ProductData PRODUCTD_YYY =  new ProductData(1, "YYY", "Lipstick", 12L);
    private static final ProductData PRODUCTD_ZZZ =  new ProductData(2, "ZZZ", "Volvo", 500L);
    private static final ProductData PRODUCT_YYY_EXT =  new ProductData(0, "YYY", "Cheaper lipstick", 1L);
    private static final ProductData PRODUCT_ZZZ_EXT =  new ProductData(0, "ZZZ", "Better volvo", 501L);

    @TestConfiguration
    static class ProductPackageServiceTestContextConfiguration {
        @Bean
        public ProductPackageService packageService() {
            return new ProductPackageServiceImpl();
        }
    }
    @Autowired
    ProductPackageService packageService;

    @MockBean
    private ProductPackageRepository repository;
    @MockBean
    private ProductService productService;
    @MockBean
    private ExchangeRateService exchangeRateService;


    @Test
    public void testRetrievePackageWithCurrencyConversion() {
        ReflectionTestUtils.setField(packageService, "defaultCurrency", "USD");
        Product px = new Product(PRODUCT_XXX);
        Product py = new Product(PRODUCT_YYY);
        ProductPackage pack = new ProductPackage(100L, "pName", "pDesc", Arrays.asList(new Product[]{px, py}));
        Long id = 3L;
        BigDecimal rate = new BigDecimal("2.89");
        when(repository.findOne(id)).thenReturn(Optional.of(pack));
        when(exchangeRateService.getExchangeRate("USD", "EUR")).thenReturn(rate);

        ProductPackageDataOut returned = packageService.retrievePackage(id, "EUR");

        assertEquals(2, returned.getProducts().size());
        assertEquals(new BigDecimal("5.29"), returned.getPriceInCurrency());
        assertEquals("EUR", returned.getCurrency());
        assertEquals(100L, returned.getId().longValue());
        assertEquals("pName", returned.getName());
        assertEquals("Lipstick", returned.getProducts().get(1).getName());
    }
    @Test
    public void testUpdatePackage_whatIsSavedToRepo() {
        ReflectionTestUtils.setField(packageService, "defaultCurrency", "USD");
        //old data
        Product px = PRODUCT_XXX;
        Product py = PRODUCT_YYY;
        ProductPackage pack = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px, py}));
        Long id = 3L;
        //new data
        //product XXX removed
        ProductData pdz = PRODUCTD_ZZZ; //added
        ProductData pdy = PRODUCTD_YYY; //updated count
        ProductPackageDataIn packIn = new ProductPackageDataIn("pName2", "pDesc2", Arrays.asList(new ProductData[]{pdz, pdy}));

        when(repository.findOne(id)).thenReturn(Optional.of(pack));
        when(repository.save(isA(ProductPackage.class))).thenReturn(pack);
        when(productService.getProductById("ZZZ")).thenReturn(PRODUCT_ZZZ_EXT);
        when(productService.getProductById("YYY")).thenReturn(PRODUCT_YYY_EXT);

        this.packageService.updatePackage(id, packIn);

        ArgumentCaptor<ProductPackage> savedArgument = ArgumentCaptor.forClass(ProductPackage.class);
        verify(repository, times(1)).save(savedArgument.capture());
        ProductPackage savedPackage = savedArgument.getValue();
        assertEquals("pName2", savedPackage.getName());
        assertEquals(2, savedPackage.getProducts().size());
        assertEquals(1, savedPackage.getProducts().get(0).getCount());
        assertEquals("YYY", savedPackage.getProducts().get(0).getProductId());
        assertEquals(1L, savedPackage.getProducts().get(0).getUsdPrice().longValue());
        assertEquals("ZZZ", savedPackage.getProducts().get(1).getProductId());
        assertEquals("Better volvo", savedPackage.getProducts().get(1).getName());

    }
}
