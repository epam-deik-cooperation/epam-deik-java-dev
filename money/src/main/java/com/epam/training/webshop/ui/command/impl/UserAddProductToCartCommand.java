package com.epam.training.webshop.ui.command.impl;

import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import com.epam.training.webshop.ui.session.Session;
import java.util.Optional;

public class UserAddProductToCartCommand extends AbstractCommand {

    private final ProductService productService;

    public UserAddProductToCartCommand(ProductService productService) {
        super("user", "cart", "addProduct");
        this.productService = productService;
    }

    @Override
    public String process(String[] params) {
        String resultString = params[0] + " is added to your cart";
        Optional<Product> optionalProduct = productService.getProductByName(params[0]);
        if (optionalProduct.isEmpty()) {
            resultString = params[0] + " is not found as a Product";
        } else {
            Session.INSTANCE.getCart().add(optionalProduct.get());
        }
        return resultString;
    }
}
