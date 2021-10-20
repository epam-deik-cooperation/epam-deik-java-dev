package com.epam.training.webshop.ui.command.impl;

public class BaseCommand extends AbstractCommand {

    public BaseCommand(String userType, String entityType, String action) {
        super(userType, entityType, action);
    }

    @Override
    public String process(String[] params) {
        throw new UnsupportedOperationException();
    }
}
