package com.epam.training.taxi;

import com.epam.training.taxi.invoice.CsvInvoiceWriter;
import com.epam.training.taxi.invoice.Invoice;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvInvoiceWriterTest {

    private final String PATH_TO_FILE = "./test_output.csv";
    private final CsvInvoiceWriter underTest = new CsvInvoiceWriter(PATH_TO_FILE);

    @Test
    public void testWriteShouldWriteCorrectValueToFileWhenGivenValidInvoice() throws IOException {

        // Given
        Invoice invoice = new Invoice("5", 10.0, 1000.0, 100.0);

        // When
        underTest.write(invoice);

        // Then
        String lastOutput = Files.lines(Path.of(PATH_TO_FILE))
                .reduce((first, second) -> second)
                .orElse(null);

        assertEquals("5,10.0,1000.0,100.0", lastOutput);
    }
}
