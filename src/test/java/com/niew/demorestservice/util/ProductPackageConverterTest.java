package com.niew.demorestservice.util;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageDataOut;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductPackageConverterTest {

    private static final Product PRODUCT_XXX =  new Product(1, "XXX", "Shoes", 123L);
    private static final Product PRODUCT_XXX_2 =  new Product(2, "XXX", "Shoes", 123L);
    private static final Product PRODUCT_YYY = new Product(5, "YYY", "Lipstick", 12L);
    private static final ProductData PRODUCTD_XXX =  new ProductData(1, "XXX", "Shoes", 123L);
    private static final ProductData PRODUCTD_XXX_2 =  new ProductData(2, "XXX", "Shoes", 123L);
    private static final ProductData PRODUCTD_YYY = new ProductData(5, "YYY", "Lipstick", 12L);
    private static final BigDecimal CURR_EXCH = new BigDecimal("0.891");

    @Test
    public void testConvertPackageToDto() {
        Product px = new Product(PRODUCT_XXX);
        Product py = new Product(PRODUCT_YYY);
        ProductPackage pack = new ProductPackage(100L, "pName", "pDesc", Arrays.asList(new Product[]{px, py}));
        assertEquals(183L, pack.getPrice().longValue());
        ProductPackageDataOut dto = ProductPackageConverter.convertToDTO(pack, CURR_EXCH, "curr");
        assertEquals("curr", dto.getCurrency());
        assertEquals(new BigDecimal("1.63"), dto.getPriceInCurrency());
        assertEquals("pName", dto.getName());
        assertEquals("pDesc", dto.getDescription());
        assertEquals(100L, dto.getId().longValue());
        assertEquals(2, dto.getProducts().size());
        assertEquals(5, dto.getProducts().get(1).getCount());
        assertEquals("XXX", dto.getProducts().get(0).getProductId());
    }
    @Test
    public void testConvertProductListFromDto() {
        List<Product> result = ProductPackageConverter.convertProductsFromDtoGrouping(Arrays.asList(new ProductData[]{PRODUCTD_XXX, PRODUCTD_XXX_2, PRODUCTD_YYY}));
        assertEquals(2, result.size());
        assertEquals("XXX", result.get(0).getProductId());
        assertEquals(3, result.get(0).getCount());
        assertEquals("YYY", result.get(1).getProductId());
        assertEquals(5, result.get(1).getCount());
    }

}
