package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Optional;

public class UserCartRemoveProductCommand extends AbstractCommand {

    private final ProductService productService;
    private final Cart cart;

    public UserCartRemoveProductCommand(ProductService productService, Cart cart) {
        super("user", "cart", "removeProduct");
        this.productService = productService;
        this.cart = cart;
    }

    @Override
    protected String process(String[] params) {
        String productName = params[0];
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        }
        if (cart.getProductList().contains(optionalProduct.get())) {
            cart.removeProduct(optionalProduct.get());
            return productName + " is removed from your cart!";
        } else {
            return productName + " is not in your cart!";
        }
    }
}
