package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.presentation.cli.command.Command;

public class OrderCommand implements Command {

    private final ShoppingCartService shoppingCartService;

    public OrderCommand(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public String execute() {
        double value = shoppingCartService.getTotalNetPrice();
        double taxedTotalValue = shoppingCartService.getTotalGrossPrice();
        shoppingCartService.order();
        return "Ordered a cart worth (netPrice):" + value + " with tax (grossPrice): " + taxedTotalValue;
    }
}
