package com.epam.training.webshop.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double netPrice;

    protected Product() {

    }

    public Product(String name, double netPrice) {
        this.name = name;
        this.netPrice = netPrice;
    }

    public Product(final SimpleProductBuilder simpleProductBuilder) {
        this.name = simpleProductBuilder.name;
        this.netPrice = simpleProductBuilder.netPrice;
    }

    public static SimpleProductBuilder builder() {
        return new SimpleProductBuilder();
    }

    public String getName() {
        return name;
    }

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
        Product that = (Product) o;
        return Double.compare(that.netPrice, netPrice) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, netPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
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

        public Product build() {
            return new Product(this);
        }
    }
}
