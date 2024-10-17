package com.epam.training.money;

import com.epam.training.money.interpreter.CommandLineInterpreter;
import com.epam.training.money.interpreter.CommandLineInterpreterFactory;

public class Application {

  public static void main(String[] args) {
    CommandLineInterpreter interpreter =
        CommandLineInterpreterFactory.createInterpreter();
    interpreter.handleUSerInput();
  }
}
