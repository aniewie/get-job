package com.niew.demorestservice.domain;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ProductPackageTest {

    @Test
    public void testCreateProductPackage() {
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L)}));
        int productTypesCount = packageWithOneProduct.getProducts().size();
        long packagePrice = packageWithOneProduct.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(123L, packagePrice);
    }
    @Test
    public void testAddingProductToPackage() {
        ProductPackage packageWithOneProduct = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L)}));
        packageWithOneProduct.update("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L), new Product(1, "XXX", "Lipstick", 12L)}));
        int productTypesCount = packageWithOneProduct.getProducts().size();
        long packagePrice = packageWithOneProduct.getPrice();
        assertEquals(2, productTypesCount);
        assertEquals(135L, packagePrice);
    }
    @Test
    public void testRemovingProductFromPackage() {
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L), new Product(1, "XXX", "Lipstick", 12L)}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L)}));;
        int productTypesCount = packageWithTwoProducts.getProducts().size();
        long packagePrice = packageWithTwoProducts.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(123L, packagePrice);
    }
    @Test
    public void testModifyingProductInPackage() {
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L), new Product(1, "XXX", "Lipstick", 12L)}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{new Product(2, "YYY", "Nice shoes", 123L)}));;
        int productTypesCount = packageWithTwoProducts.getProducts().size();
        long packagePrice = packageWithTwoProducts.getPrice();
        assertEquals(1, productTypesCount);
        assertEquals(246L, packagePrice);
        assertEquals(2, packageWithTwoProducts.getProducts().get(0).getCount());
        assertEquals("Nice shoes", packageWithTwoProducts.getProducts().get(0).getName());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddingDuplicatedProductsToPackage() {
        ProductPackage packageWithTwoProducts = new ProductPackage("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L), new Product(1, "XXX", "Lipstick", 12L)}));;
        packageWithTwoProducts.update("pName", "pDesc", Arrays.asList(new Product[]{new Product(1, "YYY", "Shoes", 123L), new Product(1, "XXX", "Lipstick", 12L), new Product(2, "XXX", "Lipstick", 12L)}));;
    }
}
