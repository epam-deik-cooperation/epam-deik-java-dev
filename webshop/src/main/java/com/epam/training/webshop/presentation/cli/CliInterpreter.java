package com.epam.training.webshop.presentation.cli;

import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class CliInterpreter {

    private final BufferedReader input;
    private final Writer output;
    private boolean shouldRun;
    private CommandLineParser commandLineParser;

    public CliInterpreter(Reader input, Writer output) {
        this.input = new BufferedReader(input);
        this.output = output;
        commandLineParser = null;
        shouldRun = false;
    }

    public void updateCommandLineParser(CommandLineParser commandLineParser) {
        this.commandLineParser = commandLineParser;
    }

    public void start() throws IOException {
        if (commandLineParser == null) {
            throw new IllegalStateException("Attempted starting the Cli Interpreter without a command line parser");
        }
        shouldRun = true;
        while (shouldRun) {
            String commandLine = input.readLine();
            String result = commandLineParser.createCommand(commandLine).execute();
            output.write(result + System.lineSeparator());
            output.flush();
        }
    }

    public void stop() {
        shouldRun = false;
    }
}
