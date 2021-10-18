package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.presentation.cli.command.AbstractCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.Command;

public class OrderCommandLineParser extends AbstractCommandLineParser {

    private static final String ORDER_COMMAND = "order basket";

    private final Cart cartToOrder;

    public OrderCommandLineParser(Cart cartToOrder) {
        this.cartToOrder = cartToOrder;
    }

    @Override
    protected boolean canCreateCommand(String commandLine) {
        return ORDER_COMMAND.equals(commandLine);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        return new OrderCommand(cartToOrder);
    }
}
