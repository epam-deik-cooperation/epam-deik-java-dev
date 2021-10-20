package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final List<Product> productList = List.of(
        Product.builder()
            .withName("GPU")
            .withNetPrice(new Money(600_000, Currency.getInstance("HUF")))
            .build(),
        Product.builder()
            .withName("PS5")
            .withNetPrice(new Money(500_000, Currency.getInstance("HUF")))
            .build()
    );

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Optional<Product> getProductByName(String productName) {
        return productList.stream()
            .filter(name -> name.getName().equals(productName))
            .findFirst();
    }
}
