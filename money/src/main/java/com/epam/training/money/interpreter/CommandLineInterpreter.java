package com.epam.training.money.interpreter;

import com.epam.training.money.command.AbstractCommand;
import java.util.Scanner;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandLineInterpreter {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final Set<AbstractCommand> abstractCommands;

    public void handleUserInputs() {
        String inputLine;
        while (!"exit".equals(inputLine = SCANNER.nextLine())) {
            String[] commandParts = inputLine.split(" ");
            if (commandParts.length < 3) {
                throw new IllegalArgumentException("You must provide at least 3 parameters!");
            }
            AbstractCommand command = getCommandFromParts(commandParts);
            System.out.println(command.process(inputLine));
        }
    }

    private AbstractCommand getCommandFromParts(String[] params) {
        AbstractCommand abstractCommand = new AbstractCommand(params[0], params[1], params[2]) {
            @Override
            public String process(String[] params) {
                return null;
            }
        };
        return abstractCommands
            .stream()
            .filter(command -> command.equals(abstractCommand))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Command is not found!"));
    }
}
