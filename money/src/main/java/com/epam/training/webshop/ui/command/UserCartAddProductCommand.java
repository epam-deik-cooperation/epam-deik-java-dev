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
    protected String process(String[] params) {
        String productName = params[0];
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        } else {
            cart.addProduct(optionalProduct.get());
            return productName + " is added to your cart!";
        }
    }
}
