package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.presentation.cli.command.Command;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.Optional;

public class AddProductCommand implements Command {

    private final ProductRepository productRepository;
    private final Cart cart;
    private final String productNameToAdd;

    public AddProductCommand(ProductRepository productRepository, Cart cart, String productNameToAdd) {
        this.productRepository = productRepository;
        this.cart = cart;
        this.productNameToAdd = productNameToAdd;
    }

    @Override
    public String execute() {
        Optional<Product> productToAdd = productRepository.getAllProduct()
                .stream()
                .filter(product -> product.getName().equals(productNameToAdd))
                .findFirst();
        if (productToAdd.isEmpty()) {
            return "No such product";
        }
        cart.addProduct(productToAdd.get());
        return "Alright.";
    }
}
