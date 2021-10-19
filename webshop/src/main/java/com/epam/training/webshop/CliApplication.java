package com.epam.training.webshop;

import com.epam.training.webshop.config.CliConfiguration;
import java.io.IOException;

public class CliApplication {

    public static void main(String[] args) throws IOException {
        CliConfiguration.cliInterpreter().start();
    }
}
