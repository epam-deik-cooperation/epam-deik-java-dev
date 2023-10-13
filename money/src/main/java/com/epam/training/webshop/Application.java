package com.epam.training.webshop;

import com.epam.training.webshop.ui.interpreter.CommandLineInterpreter;
import com.epam.training.webshop.ui.interpreter.CommandLineInterpreterFactory;

public class Application {

    public static void main(String[] args) {
        CommandLineInterpreter interpreter = CommandLineInterpreterFactory.create();
        interpreter.handleUserInputs();
    }
}
