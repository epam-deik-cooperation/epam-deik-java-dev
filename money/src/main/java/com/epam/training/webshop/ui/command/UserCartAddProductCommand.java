package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import com.epam.training.webshop.ui.session.Session;
import java.util.Optional;

public class UserCartAddProductCommand extends AbstractCommand {

    private final ProductService productService;

    public UserCartAddProductCommand(ProductService productService) {
        super("user", "cart", "addProduct");
        this.productService = productService;
    }

    @Override
    protected String process(String[] params) {
        String productName = params[0];
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        } else {
            Session.INSTANCE.getCart().addProduct(optionalProduct.get());
            return productName + " is added to your cart!";
        }
    }
}
