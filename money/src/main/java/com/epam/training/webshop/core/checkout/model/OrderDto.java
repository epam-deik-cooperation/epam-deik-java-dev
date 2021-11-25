package com.epam.training.webshop.core.checkout.model;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private final List<ProductDto> productList;
    private final Money netPrice;
    private final Money grossPrice;

    public OrderDto(List<ProductDto> productList, Money netPrice, Money grossPrice) {
        this.productList = productList;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public Money getNetPrice() {
        return netPrice;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(productList, orderDto.productList) && Objects.equals(netPrice, orderDto.netPrice) && Objects.equals(grossPrice, orderDto.grossPrice);
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
