package com.epam.training.taxi;

import java.io.IOException;

public interface DataWriter {
    void appendLine(String[] data) throws IOException;
}
