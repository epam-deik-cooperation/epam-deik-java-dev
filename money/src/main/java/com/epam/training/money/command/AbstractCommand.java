package com.epam.training.money.command;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractCommand {

  private final String user;
  private final String entityType;
  private final String action;

  public String process(String input) {
    String[] commands = input.split(" ");
    return process(Arrays.copyOfRange(commands, 3, commands.length));
  }

  protected abstract String process(String[] commands);

}
