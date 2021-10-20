package com.epam.training.webshop.ui.interpreter;

import com.epam.training.webshop.ui.command.impl.AbstractCommand;
import com.epam.training.webshop.ui.command.impl.BaseCommand;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class CommandLineInterpreter {

    private final Scanner scanner;
    private final OutputStream outputStream;
    private final Set<AbstractCommand> commandSet;

    public CommandLineInterpreter(InputStream inputStream, OutputStream outputStream, Set<AbstractCommand> commandSet) {
        this.scanner = new Scanner(inputStream);
        this.outputStream = outputStream;
        this.commandSet = commandSet;
    }

    public void handleUserInputs() throws IOException {
        String inputLine;
        while (!"exit".equals(inputLine = scanner.nextLine())) {
            String[] commandParts = inputLine.split(" ");
            if (commandParts.length < 3) {
                throw new IllegalArgumentException("You must provide at least 3 parameters!");
            }
            Optional<AbstractCommand> command = getCommandFromParts(commandParts);
            if (command.isEmpty()) {
                throw new IllegalArgumentException("You have provided an invalid command: " + inputLine);
            } else {
                outputStream.write(command.get().process(inputLine).getBytes());
                outputStream.write(System.lineSeparator().getBytes());
            }
        }
    }

    private Optional<AbstractCommand> getCommandFromParts(String[] commandParts) {
        AbstractCommand abstractCommand = new BaseCommand(commandParts[0], commandParts[1], commandParts[2]);
        return commandSet.stream().filter(command -> command.equals(abstractCommand)).findFirst();
    }
}
