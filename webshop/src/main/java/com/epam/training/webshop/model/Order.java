package com.epam.training.webshop.model;

import java.util.List;
import java.util.Objects;

public class Order {

    private final List<Product> orderedProducts;
    private final double fullNetPrice;
    private final double fullGrossPrice;

    public Order(final List<Product> orderedProducts, final double fullNetPrice, final double fullGrossPrice) {
        this.orderedProducts = orderedProducts;
        this.fullNetPrice = fullNetPrice;
        this.fullGrossPrice = fullGrossPrice;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public double getFullNetPrice() {
        return fullNetPrice;
    }

    public double getFullGrossPrice() {
        return fullGrossPrice;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        final Order order = (Order) o;
        return Double.compare(order.fullNetPrice, fullNetPrice) == 0 && Double.compare(order.fullGrossPrice, fullGrossPrice) == 0 && orderedProducts.equals(order.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedProducts, fullNetPrice, fullGrossPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderedProducts=" + orderedProducts +
                ", fullNetPrice=" + fullNetPrice +
                ", fullGrossPrice=" + fullGrossPrice +
                '}';
    }
}
