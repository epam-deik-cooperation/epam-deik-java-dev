package com.epam.training.ticketservice.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommands {

    @ShellMethod(key = "exit", value = "Exit the application.")
    public void exit() {
        System.exit(0);
    }
}
