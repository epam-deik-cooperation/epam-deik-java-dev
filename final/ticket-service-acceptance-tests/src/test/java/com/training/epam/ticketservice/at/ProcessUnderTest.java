package com.training.epam.ticketservice.at;

import java.io.*;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.SimpleTimeLimiter;

public class ProcessUnderTest implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessUnderTest.class);

    private Process process;
    private BufferedReader output;
    private Writer input;

    public void run(String command) throws IOException {
        if (this.process != null && process.isAlive()) {
            return;
        }
        process = Runtime.getRuntime().exec(command);
        output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        input = new OutputStreamWriter(process.getOutputStream());
    }

    public void waitForOutput(String expectedOutput, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        runWithTimeout(() -> readOutputUntil(expectedOutput), timeout);
    }

    public String readNextLine(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        return runWithTimeout(() -> output.readLine(), timeout);
    }


    @Override
    public void close() {
        process.destroy();
    }
    public void writeOnInput(String command) throws IOException {
        clearOutput(100);
        input.write(command+System.lineSeparator());
        input.flush();
    }

    private void clearOutput(long delayBeforeCleaning) throws IOException {
        try {
            Thread.sleep(delayBeforeCleaning);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (output.ready()) {
            output.read();
        }
    }

    private Void readOutputUntil(String expectedOutput) throws IOException {
        String actualString = "";
        do {
            actualString += (char) output.read();
            if (actualString.length()>expectedOutput.length()) {
                actualString = actualString.substring(1);
            }
        } while (!actualString.equals(expectedOutput));
        return null; // Void for the sake of generics
    }

    private <T> T runWithTimeout(Callable<T> callable, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        SimpleTimeLimiter timeLimiter = SimpleTimeLimiter.create(Executors.newSingleThreadExecutor());
        return timeLimiter.callWithTimeout(callable, timeout, TimeUnit.MILLISECONDS);
    }
}
