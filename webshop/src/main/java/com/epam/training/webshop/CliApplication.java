package com.epam.training.webshop;

import com.epam.training.webshop.config.WebShopConfiguration;
import com.epam.training.webshop.presentation.cli.CliInterpreter;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CliApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebShopConfiguration.class);
        CliInterpreter interpreter = applicationContext.getBean(CliInterpreter.class);
        interpreter.start();
    }
}
