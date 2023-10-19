package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Optional;

public class UserCartAddProductCommand extends AbstractCommand {

    private final ProductService productService;
    private final Cart cart;

    public UserCartAddProductCommand(ProductService productService, Cart cart) {
        super("user", "cart", "addProduct");
        this.productService = productService;
        this.cart = cart;
    }

    @Override
    protected String process(String[] commandParts) {
        String productName = commandParts[0];
        int amount = Integer.parseInt(commandParts[1]);
        Optional<Product> product = productService.getProductByName(productName);
        if (product.isEmpty()) {
            return productName + " is not found!";
        } else {
            cart.addProduct(product.get(), amount);
            return productName + " is added to your cart!";
        }
    }
}
