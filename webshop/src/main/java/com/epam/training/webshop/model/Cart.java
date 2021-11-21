package com.epam.training.webshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE)
    private final List<Product> products;

    @OneToMany(cascade = CascadeType.REMOVE)
    private final List<Coupon> coupons;

    public Cart() {
        this.products = new ArrayList<>();
        this.coupons = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void addProduct(final Product product) {
        products.add(product);
    }

    public void removeProduct(final Product product) {
        products.remove(product);
    }

    public void addCoupon(final Coupon coupon) {
        coupons.add(coupon);
    }

    public void removeCoupon(final Coupon coupon) {
        coupons.remove(coupon);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        final Cart cart = (Cart) o;
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
