package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.exception.NoSuchProductException;
import com.epam.training.webshop.presentation.cli.command.Command;

public class AddProductCommand implements Command {

    private final ShoppingCartService shoppingCartService;
    private final String productNameToAdd;

    public AddProductCommand(ShoppingCartService shoppingCartService, String productNameToAdd) {
        this.shoppingCartService = shoppingCartService;
        this.productNameToAdd = productNameToAdd;
    }

    @Override
    public String execute() {
        try {
            shoppingCartService.addProduct(productNameToAdd);
            return "Alright.";
        } catch (NoSuchProductException exception) {
            return "No such product";
        }
    }
}
