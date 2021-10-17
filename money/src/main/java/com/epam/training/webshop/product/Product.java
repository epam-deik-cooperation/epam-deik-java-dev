package com.epam.training.webshop.product;

import com.epam.training.webshop.finance.money.Money;
import java.util.Objects;

public class Product {

    private final String name;
    private final Money netPrice;

    public Product(String name, Money netPrice) {
        this.name = name;
        this.netPrice = netPrice;
    }

    public String getName() {
        return name;
    }

    public Money getNetPrice() {
        return netPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(netPrice, product.netPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, netPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
            "name='" + name + '\'' +
            ", netPrice=" + netPrice +
            '}';
    }
}