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
    protected String process(String[] commandParts) {
        String productName = commandParts[0];
        Optional<Product> product = productService.getProductByName(productName);
        if (product.isEmpty()) {
            return productName + " is not found!";
        }
        if (cart.containsProduct(product.get())) {
            cart.removeProduct(product.get());
            return "Product removed from the cart!";
        }
        return "Product is not found in the cart!";
    }
}
