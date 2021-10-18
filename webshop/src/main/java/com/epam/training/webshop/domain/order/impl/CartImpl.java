package com.epam.training.webshop.domain.order.impl;

import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.Coupon;
import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.repository.OrderRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartImpl implements Cart {

    private final List<Observer> observers;

    private final List<Product> products;
    private final List<Coupon> coupons;

    private final GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
    private final OrderRepository orderRepository;

    public CartImpl(OrderRepository orderRepository, GrossPriceCalculatorDecorator grossPriceCalculatorDecorator) {
        this.orderRepository = orderRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        products = new ArrayList<>();
        coupons = new ArrayList<>();
        observers = new LinkedList<>();
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getProductsFromBasket() {
        return products;
    }

    @Override
    public void removeProduct(Product productToRemove) {
        products.remove(productToRemove);
    }

    @Override
    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromBasket() {
        return coupons;
    }

    @Override
    public double getTotalNetPrice() {
        double basePrice = getBasePrice();
        double discount = getDiscountForCoupons();
        return basePrice - discount;
    }

    @Override
    public double getTotalGrossPrice() {
        return grossPriceCalculatorDecorator.getAggregatedGrossPrice(this);
    }

    @Override
    public void order() {
        orderRepository.saveOrder(this);
        observers.forEach(observer -> observer.notify(this));
    }

    @Override
    public double getBasePrice() {
        double basePrice = 0;
        for (Product currentProduct : products) {
            basePrice += currentProduct.getNetPrice();
        }
        return basePrice;
    }

    @Override
    public double getDiscountForCoupons() {
        double discount = 0;
        for (Coupon coupon : coupons) {
            discount += coupon.getDiscountForProducts(products);
        }
        return discount;
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
