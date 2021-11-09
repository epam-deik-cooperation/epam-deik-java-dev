package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.presentation.cli.command.AbstractCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.Command;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class OrderCommandLineParser extends AbstractCommandLineParser {

    private static final String ORDER_COMMAND = "order cart";

    private final ShoppingCartService shoppingCartService;

    public OrderCommandLineParser(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    protected boolean canCreateCommand(String commandLine) {
        return ORDER_COMMAND.equals(commandLine);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        return new OrderCommand(shoppingCartService);
    }
}
