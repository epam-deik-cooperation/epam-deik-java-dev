package com.epam.training.webshop.domain.order.model.impl;

import com.epam.training.webshop.domain.order.Coupon;
import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.domain.order.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class SimpleCart implements Cart {

    private final List<Product> products;
    private final List<Coupon> coupons;

    public SimpleCart() {
        this.products = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public void addProduct(final Product product) {
        products.add(product);
    }

    @Override
    public void removeProduct(final Product product) {
        products.remove(product);
    }

    @Override
    public void addCoupon(final Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public void removeCoupon(final Coupon coupon) {
        coupons.remove(coupon);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleCart)) {
            return false;
        }
        final SimpleCart cart = (SimpleCart) o;
        return Objects.equals(products, cart.products) && Objects.equals(coupons, cart.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, coupons);
    }

    @Override
    public String toString() {
        return "CartImpl{" +
                "products=" + products +
                ", coupons=" + coupons +
                '}';
    }
}
