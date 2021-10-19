package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.presentation.cli.command.AbstractCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.Command;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProductCommandLineParser extends AbstractCommandLineParser {

    private static final String COMMAND_REGEX = "add product (.+)";
    private final ProductRepository productRepository;
    private final Cart cart;

    public AddProductCommandLineParser(ProductRepository productRepository, Cart cart) {
        this.productRepository = productRepository;
        this.cart = cart;
    }

    @Override
    protected boolean canCreateCommand(String commandLine) {
        return commandLine.matches(COMMAND_REGEX);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        Matcher matcher = Pattern.compile(COMMAND_REGEX).matcher(commandLine);
        matcher.matches();
        String productName = matcher.group(1);
        return new AddProductCommand(productRepository, cart, productName);
    }
}
