package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.presentation.cli.command.Command;

public class OrderCommand implements Command {

    private final Cart cartToOrder;

    public OrderCommand(Cart cartToOrder) {
        this.cartToOrder = cartToOrder;
    }

    @Override
    public String execute() {
        double value = cartToOrder.getTotalNetPrice();
        double taxedTotalValue = cartToOrder.getTotalGrossPrice();
        cartToOrder.order();
        return "Ordered a basket worth (netPrice):" + value + " with tax (grossPrice): " + taxedTotalValue;
    }
}
