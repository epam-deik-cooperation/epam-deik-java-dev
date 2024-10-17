package com.epam.training.money.interpreter;

import com.epam.training.money.command.AbstractCommand;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandLineInterpreter {

  private final List<AbstractCommand> abstractCommands;
  private static final Scanner SCANNER = new Scanner(System.in);

  public void handleUSerInput() {
    String line;
    while (!"exit".equals(line = SCANNER.nextLine())) {
      String[] commandParts = line.split(" ");

      if (commandParts.length < 3) {
        throw new IllegalArgumentException("The command must have at least 3 parts.");
      }

      AbstractCommand command = getAbstractCommand(commandParts);

      System.out.println(command.process(line));
    }

  }

  private AbstractCommand getAbstractCommand(String[] commandParts) {
    AbstractCommand abstractCommand = new AbstractCommand(commandParts[0], commandParts[1],
        commandParts[2]) {
      @Override
      protected String process(String[] commands) {
        return null;
      }
    };

    return abstractCommands
        .stream()
        .filter(command -> command.equals(abstractCommand))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No such command!"));
  }

}
