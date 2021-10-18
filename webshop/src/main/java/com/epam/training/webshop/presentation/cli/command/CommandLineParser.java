package com.epam.training.webshop.presentation.cli.command;

public interface CommandLineParser {

    Command createCommand(String commandLine);

    void setSuccessor(CommandLineParser successor);
}
