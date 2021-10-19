package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.AbstractCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.Command;

public class ExitCommandLineParser extends AbstractCommandLineParser {

    private static final String EXIT_COMMAND = "exit";
    private final CliInterpreter cliInterpreterToExitFrom;

    public ExitCommandLineParser(CliInterpreter cliInterpreterToExitFrom) {
        this.cliInterpreterToExitFrom = cliInterpreterToExitFrom;
    }

    @Override
    protected boolean canCreateCommand(String commandLine) {
        return EXIT_COMMAND.equals(commandLine);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        return new ExitCommand(cliInterpreterToExitFrom);
    }
}
