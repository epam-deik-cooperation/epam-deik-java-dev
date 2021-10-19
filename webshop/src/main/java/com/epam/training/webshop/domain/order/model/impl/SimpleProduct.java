package com.epam.training.webshop.domain.order.model.impl;

import com.epam.training.webshop.domain.order.model.Product;
import java.util.Objects;

public class SimpleProduct implements Product {

    private final String name;
    private final double netPrice;

    public SimpleProduct(String name, double netPrice) {
        this.name = name;
        this.netPrice = netPrice;
    }

    public SimpleProduct(final SimpleProductBuilder simpleProductBuilder) {
        this.name = simpleProductBuilder.name;
        this.netPrice = simpleProductBuilder.netPrice;
    }

    public static SimpleProductBuilder builder() {
        return new SimpleProductBuilder();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getNetPrice() {
        return netPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleProduct that = (SimpleProduct) o;
        return Double.compare(that.netPrice, netPrice) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, netPrice);
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "name='" + name + '\'' +
                ", value=" + netPrice +
                '}';
    }

    public static final class SimpleProductBuilder {
        private String name;
        private double netPrice;

        private SimpleProductBuilder() {
        }

        public SimpleProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SimpleProductBuilder withValue(double money) {
            this.netPrice = money;
            return this;
        }

        public SimpleProduct build() {
            return new SimpleProduct(this);
        }
    }
}
