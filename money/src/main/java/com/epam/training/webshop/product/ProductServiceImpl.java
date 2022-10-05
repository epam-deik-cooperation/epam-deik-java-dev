package com.epam.training.webshop.product;

import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final List<Product> productList = List.of(
        Product.builder()
            .withName("Salétromsav")
            .withNetPrice(new Money(250, Currency.getInstance("HUF")))
            .build(),
        Product.builder()
            .withName("Hypo")
            .withNetPrice(new Money(550, Currency.getInstance("HUF")))
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
