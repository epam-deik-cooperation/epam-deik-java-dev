package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private final List<Product> productList = List.of(
        Product.builder()
            .withName("Banana")
            .withNetPrice(new Money(500, HUF_CURRENCY))
            .build(),
        Product.builder()
            .withName("Kecske")
            .withNetPrice(new Money(2000, HUF_CURRENCY))
            .build(),
        Product.builder()
            .withName("Krumpli")
            .withNetPrice(new Money(600, HUF_CURRENCY))
            .build(),
        Product.builder()
            .withName("Hypo")
            .withNetPrice(new Money(300, HUF_CURRENCY))
            .build()
    );

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
}
