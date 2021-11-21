package com.epam.training.webshop.presentation.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.epam.training.webshop")
public class CliApplication {

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }
}
