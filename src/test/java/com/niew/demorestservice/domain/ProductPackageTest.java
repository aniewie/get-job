package com.niew.demorestservice.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ProductPackageTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductPackageTest.class);

    private static final Product PRODUCT_XXX =  new Product(1, "XXX", "Shoes", 123L);
    private static final Product PRODUCT_XXX_CHANGED =  new Product(1, "XXX", "Better shoes", 1000L);
    private static final Product PRODUCT_XXX_3 =  new Product(3, "XXX", "Shoes", 123L);
    private static final Product PRODUCT_YYY = new Product(1, "YYY", "Lipstick", 12L);

    @Test
    public void testModifyPackage() {
        Product px = new Product(PRODUCT_XXX);
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px}));
        assertEquals("pName", packageWithOneProduct.getName());
        assertEquals("pDesc", packageWithOneProduct.getDescription());
        packageWithOneProduct.update("pName2", "pDesc2", Arrays.asList(new Product[]{px}));
        assertEquals("pName2", packageWithOneProduct.getName());
        assertEquals("pDesc2", packageWithOneProduct.getDescription());
    }
    @Test
    public void testCreatePackage() {
        Product px = new Product(PRODUCT_XXX);
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px}));
        int productTypesCount = packageWithOneProduct.getProducts().size();
        long packagePrice = packageWithOneProduct.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(PRODUCT_XXX.getUsdPrice().longValue(), packagePrice);
    }
    @Test
    public void testCreatePackageWithMoreItemsOfProduct() {
        Product px3 = new Product(PRODUCT_XXX_3);
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px3}));
        int productTypesCount = packageWithOneProduct.getProducts().size();
        long packagePrice = packageWithOneProduct.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(PRODUCT_XXX_3.getUsdPrice().longValue() * 3, packagePrice);
    }
    @Test
    public void testAddingProductToPackage() {
        Product px = new Product(PRODUCT_XXX);
        Product py = new Product(PRODUCT_YYY);
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px}));
        assertEquals(1, packageWithOneProduct.getProducts().size());
        packageWithOneProduct.update("pName", "pDesc", Arrays.asList(new Product[]{px, py}));
        int productTypesCount = packageWithOneProduct.getProducts().size();
        long packagePrice = packageWithOneProduct.getPrice();
        assertEquals(2, productTypesCount);
        assertEquals(PRODUCT_XXX.getUsdPrice().longValue() + PRODUCT_YYY.getUsdPrice().longValue(), packagePrice);
    }
    @Test
    public void testRemovingProductFromPackage() {
        Product px = new Product(PRODUCT_XXX);
        Product py = new Product(PRODUCT_YYY);
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px, py}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{px}));;
        int productTypesCount = packageWithTwoProducts.getProducts().size();
        long packagePrice = packageWithTwoProducts.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(PRODUCT_XXX.getUsdPrice().longValue(), packagePrice);
    }
    @Test
    public void testModifyingProductInPackage() {
        Product px = new Product(PRODUCT_XXX);
        Product pxc = new Product(PRODUCT_XXX_CHANGED);
        Product py = new Product(PRODUCT_YYY);
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{py, px}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{py, pxc}));;
        int productTypesCount = packageWithTwoProducts.getProducts().size();
        long packagePrice = packageWithTwoProducts.getPrice();
        assertEquals(2, productTypesCount);
        assertEquals(PRODUCT_XXX_CHANGED.getUsdPrice().longValue() + PRODUCT_YYY.getUsdPrice().longValue(), packagePrice);
        assertEquals("Better shoes", packageWithTwoProducts.getProducts().get(1).getName());
    }
    @Test
    public void testChangingProductCountInPackage() {
        Product px = new Product(PRODUCT_XXX);
        Product px3 = new Product(PRODUCT_XXX_3);
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{px3}));;
        int productTypesCount = packageWithTwoProducts.getProducts().size();
        long packagePrice = packageWithTwoProducts.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(PRODUCT_XXX_3.getUsdPrice() * 3, packagePrice);
        assertEquals(3, packageWithTwoProducts.getProducts().get(0).getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddingDuplicatedProductsToPackage() {
        Product px = new Product(PRODUCT_XXX);
        Product py = new Product(PRODUCT_YYY);
        Product pxc = new Product(PRODUCT_XXX_CHANGED);
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{px, py}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{px, pxc, py}));;
    }
}
