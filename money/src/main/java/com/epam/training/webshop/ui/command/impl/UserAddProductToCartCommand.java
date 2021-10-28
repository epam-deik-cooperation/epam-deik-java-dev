package com.epam.training.webshop.ui.command.impl;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Optional;

public class UserAddProductToCartCommand extends AbstractCommand {

    private final ProductService productService;
    private final Cart cart;

    public UserAddProductToCartCommand(ProductService productService, Cart cart) {
        super("user", "cart", "addProduct");
        this.productService = productService;
        this.cart = cart;
    }

    @Override
    public String process(String[] params) {
        String resultString = params[0] + " is added to your cart";
        Optional<Product> optionalProduct = productService.getProductByName(params[0]);
        if (optionalProduct.isEmpty()) {
            resultString = params[0] + " is not found as a Product";
        } else {
            cart.add(optionalProduct.get());
        }
        return resultString;
    }
}
