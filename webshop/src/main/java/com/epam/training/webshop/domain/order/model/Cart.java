package com.epam.training.webshop.domain.order.model;

import com.epam.training.webshop.domain.order.Coupon;
import java.util.List;

public interface Cart {

    List<Product> getProducts();

    List<Coupon> getCoupons();

    void addProduct(Product product);

    void removeProduct(Product product);

    void addCoupon(Coupon coupon);

    void removeCoupon(Coupon coupon);
}
