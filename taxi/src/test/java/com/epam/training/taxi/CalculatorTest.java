package com.epam.training.taxi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final Integer DISTANCE_MULTIPLIER = 110;

    private String outFileLastLine() throws IOException {
        List<String> fileLines = Files.lines(Path.of("out.csv"))
                .collect(Collectors.toList());
        return fileLines.get(fileLines.size() - 1);
    }

    @Test
    void testMainInsertsNullLineWhenInvalidId() throws IOException {
        // Given
        String accountId = "-1";
        String distance = "10";
        String[] args = {accountId, distance};
        String expected = "null," + Double.parseDouble(distance) + "," + Double.parseDouble(distance) * DISTANCE_MULTIPLIER + "," + 0;

        // When
        Calculator.main(args);

        // Then
        assertEquals(expected, outFileLastLine());
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/accounts.csv")
    void testMainInsertsIdAndDiscountWhenValidId(String accountId, String name, String discount) throws IOException {
        // Given
        String distance = "10";
        double multipliedDistance = Double.parseDouble(distance) * DISTANCE_MULTIPLIER;
        String[] args = {accountId, distance};
        String expected = accountId + "," + Double.parseDouble(distance) + "," +
                (multipliedDistance - multipliedDistance * Double.parseDouble(discount)) + "," +
                multipliedDistance * Double.parseDouble(discount);

        // When
        Calculator.main(args);

        // Then
        assertEquals(expected, outFileLastLine());
    }
}
