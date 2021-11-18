package com.epam.training.webshop.core.product.model;

import com.epam.training.webshop.core.finance.money.Money;
import java.util.Objects;

public class ProductDto {

    private final String name;
    private final Money netPrice;

    public ProductDto(String name, Money netPrice) {
        this.name = name;
        this.netPrice = netPrice;
    }

    public static Builder builder() {
        return new Builder();
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
        ProductDto product = (ProductDto) o;
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

    public static class Builder {
        private String name;
        private Money netPrice;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNetPrice(Money netPrice) {
            this.netPrice = netPrice;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(name, netPrice);
        }
    }
}