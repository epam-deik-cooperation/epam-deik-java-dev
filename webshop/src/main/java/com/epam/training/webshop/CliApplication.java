package com.epam.training.webshop;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.epam.training.webshop.presentation.cli.CliInterpreter;

@SpringBootApplication
public class CliApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(CliApplication.class, args);
        context.getBean(CliInterpreter.class).start();
    }
}
