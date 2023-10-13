package com.epam.training.money.command;

import com.epam.training.money.product.ProductService;

public class UserProductListCommand extends AbstractCommand {

    private final ProductService productService;

    public UserProductListCommand(ProductService productService) {
        super("user", "product", "list");
        this.productService = productService;
    }

    @Override
    protected String process(String[] commandParts) {
        return productService.getProductList().toString();
    }
}
