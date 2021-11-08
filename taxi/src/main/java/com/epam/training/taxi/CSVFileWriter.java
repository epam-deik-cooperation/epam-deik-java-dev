package com.epam.training.taxi;

import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter extends FileWriter implements DataWriter {

    private final String separator;

    public CSVFileWriter(String path, String separator) throws IOException {
        super(path, true);
        this.separator = separator;
    }

    @Override
    public void appendLine(String ...data) throws IOException {
        write(String.join(this.separator, data) + System.lineSeparator());
    }
}
