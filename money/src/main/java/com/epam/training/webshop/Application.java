package com.epam.training.webshop;

import com.epam.training.webshop.ui.interpreter.CommandLineInterpreterFactory;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        CommandLineInterpreterFactory.create(System.in, System.out).handleUserInputs();
    }
}
