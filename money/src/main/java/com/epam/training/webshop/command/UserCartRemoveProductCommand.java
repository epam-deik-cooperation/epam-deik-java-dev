package com.epam.training.webshop.command;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.product.ProductService;
import com.epam.training.webshop.product.model.Product;
import com.epam.training.webshop.session.Session;
import java.util.Optional;

public class UserCartRemoveProductCommand extends AbstractCommand {

    private final ProductService productService;

    public UserCartRemoveProductCommand(ProductService productService) {
        super("user", "cart", "removeProduct");
        this.productService = productService;
    }

    @Override
    protected String process(String[] params) {
        String productName = params[0];
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        }
        Cart cart = Session.INSTANCE.getCart();
        if (cart.getProductList().contains(optionalProduct.get())) {
            cart.removeProduct(optionalProduct.get());
            return productName + " is removed from your cart!";
        } else {
            return productName + " is not in your cart!";
        }
    }
}
