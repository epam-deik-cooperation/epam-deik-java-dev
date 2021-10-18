package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.presentation.cli.command.Command;

public class InvalidCommand implements Command {

    @Override
    public String execute() {
        return "Unknown command.";
    }
}
