package com.epam.training.webshop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Cart {

    List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    @Override
    public List<Product> listProduct() {
        return products;
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }
}
