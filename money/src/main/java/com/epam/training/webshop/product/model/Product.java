package com.epam.training.webshop.product.model;

import com.epam.training.webshop.finance.money.Money;
import lombok.Value;

@Value
public class Product {

    private final String name;
    private final Money netPrice;

    public static Builder builder() {
        return new Builder();
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

        public Product build() {
            return new Product(name, netPrice);
        }
    }
}
