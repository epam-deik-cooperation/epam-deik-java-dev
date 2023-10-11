package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import com.epam.training.webshop.ui.session.Session;
import java.util.Optional;

public class UserCartRemoveProductCommand extends AbstractCommand {

    private final ProductService productService;

    public UserCartRemoveProductCommand(ProductService productService) {
        super("user", "cart", "removeProduct");
        this.productService = productService;
    }

    @Override
    protected String process(String[] commandParts) {
        String productName = commandParts[0];
        Optional<Product> product = productService.getProductByName(productName);
        if (product.isEmpty()) {
            return productName + " is not found!";
        }
        Cart cart = Session.INSTANCE.getCart();
        if (cart.containsProduct(product.get())) {
            cart.removeProduct(product.get());
            return "Product removed from the cart!";
        }
        return "Product is not found in the cart!";
    }
}
