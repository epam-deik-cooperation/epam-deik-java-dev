package com.epam.training.webshop;

import com.epam.training.webshop.ui.interpreter.CommandLineInterpreter;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.training.webshop");
        CommandLineInterpreter commandLineInterpreter = context.getBean(CommandLineInterpreter.class);
        commandLineInterpreter.handleUserInputs();
    }
}
