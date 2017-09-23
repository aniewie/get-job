package com.niew.demorestservice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public ProductPackage(String name, String description, List<Product> products) {
        this();
        this.update(name, description, products);
    }
    public void update(String name, String description, List<Product> newProducts) {
        this.name = name;
        this.description = description;
        //this.products.clear();
        //this.products.addAll(newProducts);
        //TODO - komentarz, czemu tak
        Map<String, Product> oldProductsMap = this.products.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        Map<String, Product> newProductsMap = newProducts.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        for (Iterator<Product> iterator = this.products.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (!newProductsMap.containsKey(product.getProductId())) {
                iterator.remove();
            }
        }
        for (Product newProduct : newProducts) {
            if (oldProductsMap.containsKey(newProduct.getProductId())) {
                Product oldProduct = oldProductsMap.get(newProduct.getProductId());
                oldProduct.update(newProduct.getCount(), newProduct.getName(), newProduct.getUsdPrice());
            } else {
                this.products.add(newProduct);
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
