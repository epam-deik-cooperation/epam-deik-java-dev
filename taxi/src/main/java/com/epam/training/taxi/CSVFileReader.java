package com.epam.training.taxi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CSVFileReader implements DataReader {

    private final String path;
    private final String separator;

    CSVFileReader(String path, String separator) {
        this.path = path;
        this.separator = separator;
    }

    @Override
    public List<String[]> getAllData() {
        try {
            return Files.lines(Path.of(path))
                    .map(line -> line.split(separator))
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            System.err.println("Hiba a fájl beolvasása közben");
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<String[]> getDataByAccountId(String accountId) {
        return getAllData().stream()
                .filter(line -> accountId.equals(line[0]))
                .findFirst();
    }
}
