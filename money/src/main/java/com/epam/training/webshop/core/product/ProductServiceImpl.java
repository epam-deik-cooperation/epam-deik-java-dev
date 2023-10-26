package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private List<Product> productList;

    public void initProducts() {
        productList = new LinkedList<>(List.of(
            Product.builder()
                .withName("Pentium3")
                .withNetPrice(new Money(500, HUF_CURRENCY))
                .build(),
            Product.builder()
                .withName("Pentium4")
                .withNetPrice(new Money(5000, HUF_CURRENCY))
                .build(),
            Product.builder()
                .withName("Pentium5")
                .withNetPrice(new Money(50000, HUF_CURRENCY))
                .build()
        ));
    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        return productList.stream()
            .filter(product -> product.getName().equals(productName))
            .findFirst();
    }

    @Override
    public void createProduct(Product product) {
        productList.add(product);
    }
}
