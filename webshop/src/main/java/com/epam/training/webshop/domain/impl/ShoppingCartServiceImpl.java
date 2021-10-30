package com.epam.training.webshop.domain.impl;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.exception.NoSuchProductException;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.order.Coupon;
import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.repository.OrderRepository;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Cart cart;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final GrossPriceCalculator grossPriceCalculatorDecorator;
    private final List<Observer> observers;

    @Autowired
    public ShoppingCartServiceImpl(final Cart cart,
                                   final ProductRepository productRepository,
                                   final OrderRepository orderRepository,
                                   final GrossPriceCalculator grossPriceCalculatorDecorator) {
        this.cart = cart;
        this.productRepository = productRepository;
        this.grossPriceCalculatorDecorator = grossPriceCalculatorDecorator;
        this.orderRepository = orderRepository;
        this.observers = new ArrayList<>();
    }

    @Override
    public void order() {
        orderRepository.saveOrder(cart);
        observers.forEach(observer -> observer.notify(cart));
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
        Product productToAdd = productRepository.getAllProduct()
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
            discount += coupon.getDiscountForProducts(cart.getProducts());
        }
        return discount;
    }

    @Override
    public void subscribe(final Observer observer) {
        observers.add(observer);
    }
}
