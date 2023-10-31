package com.epam.training.webshop.core.product.model;

import com.epam.training.webshop.core.finance.money.Money;
import lombok.Value;

@Value
public class ProductDto {

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

        public ProductDto build() {
            return new ProductDto(name, netPrice);
        }
    }
}
