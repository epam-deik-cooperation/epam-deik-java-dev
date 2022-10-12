package com.epam.training.webshop.command;

import com.epam.training.webshop.product.ProductService;

public class UserProductListCommand extends AbstractCommand {

    private final ProductService productService;

    public UserProductListCommand(ProductService productService) {
        super("user", "product", "list");
        this.productService = productService;
    }


    @Override
    protected String process(String[] params) {
        return productService.getProductList().toString();
    }
}
