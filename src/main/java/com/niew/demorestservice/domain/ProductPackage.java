package com.niew.demorestservice.domain;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Entity
public class ProductPackage {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Long price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "productPackageId")
    private List<Product> products = new ArrayList<>();

    public ProductPackage(){};

    public ProductPackage(Long id, String name, String description, List<Product> products) {
        this(name, description, products);
        this.id = id;
    }
    public ProductPackage(String name, String description, List<Product> products) {
        this.update(name, description, products);
    }
    public void update(String name, String description, List<Product> newProducts) {
        this.name = name;
        this.description = description;
        //TODO komentarz
        Map<String, Product> oldProductsMap = this.products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        Map<String, Product> newProductsMap = null;
        try {
            newProductsMap = newProducts.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("Duplicate products not allowed");
        }

        //removing products
        for (Iterator<Product> iterator = this.products.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (!newProductsMap.containsKey(product.getProductId())) {
                iterator.remove();
            }
        }
        for (Product newProduct : newProducts) {
            if (oldProductsMap.containsKey(newProduct.getProductId())) {
                //updating price, name and count of existing product
                Product oldProduct = oldProductsMap.get(newProduct.getProductId());
                oldProduct.update(newProduct.getCount(), newProduct.getName(), newProduct.getUsdPrice());
            } else {
                //adding new product
                this.products.add(new Product(newProduct));
            }
        }
        this.price = this.products.stream().mapToLong(product -> product.getCount() * product.getUsdPrice()).sum();
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }

    public List<Product> getProducts() {
        return products;
    }
}
