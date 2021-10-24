package com.epam.training.taxi.invoice;

import java.io.FileWriter;
import java.io.IOException;

public class CsvInvoiceWriter implements InvoiceWriter {

    private final String pathToOutputFile;

    public CsvInvoiceWriter(String pathToOutputFile) {
        this.pathToOutputFile = pathToOutputFile;
    }

    @Override
    public void write(Invoice invoice) throws IOException {
        FileWriter CsvFileWriter = new FileWriter(pathToOutputFile, true);
        CsvFileWriter.write(invoice.getAccountId() + "," + invoice.getDistance() + "," + invoice.getPrice() + ","
                + invoice.getDiscountAmount() + System.lineSeparator());
        CsvFileWriter.close();
    }
}
