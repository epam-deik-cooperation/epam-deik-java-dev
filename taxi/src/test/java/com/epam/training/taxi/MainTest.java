package com.epam.training.taxi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainTest {

    @Test
    public void testMainShouldWriteToFileWhenGivenExistingAccountId() throws IOException {

        // Given
        String[] args = new String[]{"1", "1"};

        // When
        Main.main(args);

        // Then
        String lastOutput = getLastLineFromOutputFile();

        assertEquals("1,1.0,99.0,11.0", lastOutput);
    }

    @Test
    public void testMainShouldWriteToFileWhenGivenNonExistentAccountId() throws IOException {

        // Given
        String[] args = new String[]{"30", "1"};

        // When
        Main.main(args);

        // Then
        String lastOutput = getLastLineFromOutputFile();

        assertEquals("null,1.0,110.0,0.0", lastOutput);
    }

    private String getLastLineFromOutputFile() throws IOException {
        return Files.lines(Path.of("./out.csv"))
                .reduce((first, second) -> second)
                .orElse(null);
    }

}
