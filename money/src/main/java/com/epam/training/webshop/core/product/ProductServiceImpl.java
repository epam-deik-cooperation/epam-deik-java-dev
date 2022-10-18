package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private List<Product> productList;

    public void initProducts() {
        productList = List.of(
            Product.builder()
                .withName("Salétromsav")
                .withNetPrice(new Money(250, Currency.getInstance("HUF")))
                .build(),
            Product.builder()
                .withName("Hypo")
                .withNetPrice(new Money(550, Currency.getInstance("HUF")))
                .build());
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
}
