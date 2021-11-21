package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.model.Coupon;
import com.epam.training.webshop.model.Order;
import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.repository.CartRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.service.GrossPriceCalculator;
import com.epam.training.webshop.service.Observer;
import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.exception.NoSuchProductException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Cart cart;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final GrossPriceCalculator grossPriceCalculatorDecorator;
    private final List<Observer> observers;

    @Autowired
    public ShoppingCartServiceImpl(final Cart cart,
                                   final ProductRepository productRepository,
                                   final CartRepository cartRepository,
                                   final GrossPriceCalculator grossPriceCalculatorDecorator) {
        this.cart = cart;
        this.productRepository = productRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        this.cartRepository = cartRepository;
        this.observers = new ArrayList<>();
    }

    @Override
    public Order order() {
        cartRepository.save(cart);
        observers.forEach(observer -> observer.notify(cart));
        return new Order(cart.getProducts(), getTotalNetPrice(), getTotalGrossPrice());
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
    public void addProduct(String productName) throws NoSuchProductException {
        Product productToAdd = productRepository.findAll()
                .stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(NoSuchProductException::new);
        cart.addProduct(productToAdd);
    }

    @Override
    public List<Product> getProductsFromCart() {
        return cart.getProducts();
    }

    @Override
    public void removeProduct(final Product productToRemove) {
        cart.removeProduct(productToRemove);
    }

    @Override
    public void addCoupon(final Coupon coupon) {
        cart.addCoupon(coupon);
    }

    @Override
    public void removeCoupon(final Coupon coupon) {
        cart.removeCoupon(coupon);
    }

    @Override
    public List<Coupon> getCouponsFromCart() {
        return cart.getCoupons();
    }

    @Override
    public double getBasePrice() {
        double basePrice = 0;
        for (Product currentProduct : cart.getProducts()) {
            basePrice += currentProduct.getNetPrice();
        }
        return basePrice;
    }

    @Override
    public double getDiscountForCoupons() {
        double discount = 0;
        for (Coupon coupon : cart.getCoupons()) {
            discount += coupon.getValue();
        }
        return discount;
    }

    @Override
    public void subscribe(final Observer observer) {
        observers.add(observer);
    }
}
