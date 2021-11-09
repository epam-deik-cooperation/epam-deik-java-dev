package com.epam.training.webshop.domain;

import com.epam.training.webshop.domain.exception.NoSuchProductException;
import com.epam.training.webshop.domain.order.Coupon;
import com.epam.training.webshop.domain.order.Observable;
import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Product;
import java.util.List;

public interface ShoppingCartService extends Observable {

    void order();

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
