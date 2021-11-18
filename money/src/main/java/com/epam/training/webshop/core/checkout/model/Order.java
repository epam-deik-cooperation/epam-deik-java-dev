package com.epam.training.webshop.core.checkout.model;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.List;
import java.util.Objects;

public class Order {

    private final List<ProductDto> productList;
    private final Money netPrice;
    private final Money grossPrice;

    public Order(List<ProductDto> productList, Money netPrice, Money grossPrice) {
        this.productList = productList;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(productList, order.productList) && Objects.equals(netPrice, order.netPrice) && Objects.equals(grossPrice, order.grossPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productList, netPrice, grossPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
            "productList=" + productList +
            ", netPrice=" + netPrice +
            ", grossPrice=" + grossPrice +
            '}';
    }
}
