package com.epam.training.webshop.presentation.cli.command.impl;

import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.Command;

public class ExitCommand implements Command {

    private final CliInterpreter cliInterpreterToExitFrom;

    public ExitCommand(CliInterpreter cliInterpreterToExitFrom) {
        this.cliInterpreterToExitFrom = cliInterpreterToExitFrom;
    }

    @Override
    public String execute() {
        cliInterpreterToExitFrom.stop();
        return "Exiting.";
    }
}
