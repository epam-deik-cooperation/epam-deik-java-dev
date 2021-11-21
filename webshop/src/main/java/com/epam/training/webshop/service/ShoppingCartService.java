package com.epam.training.webshop.service;

import com.epam.training.webshop.model.Coupon;
import com.epam.training.webshop.model.Order;
import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.service.exception.NoSuchProductException;

import java.util.List;

public interface ShoppingCartService extends Observable {

    Order order();

    double getTotalNetPrice();

    double getTotalGrossPrice();

    void addProduct(String productName) throws NoSuchProductException;

    List<Product> getProductsFromCart();

    void removeProduct(Product productToRemove);

    void addCoupon(Coupon coupon);

    void removeCoupon(Coupon coupon);

    List<Coupon> getCouponsFromCart();

    double getBasePrice();

    double getDiscountForCoupons();

    void subscribe(Observer observer);
}
