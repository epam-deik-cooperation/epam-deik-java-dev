package com.epam.training.webshop.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.exception.NoSuchProductException;
import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.repository.ProductRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private Cart cart;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartServiceImpl(Cart cart, ProductRepository productRepository) {
        this.cart = cart;
        this.productRepository = productRepository;
    }

    @Override
    public void order() {
        cart.order();
    }

    @Override
    public double getTotalNetPrice() {
        return cart.getTotalNetPrice();
    }

    @Override
    public double getTotalGrossPrice() {
        return cart.getTotalGrossPrice();
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
}
