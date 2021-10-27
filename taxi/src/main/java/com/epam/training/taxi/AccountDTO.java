package com.epam.training.taxi;

import java.io.IOException;
import java.util.List;

public interface AccountDTO {

    List<Account> readFromCSV(String path) throws IOException;

    void writeToCSV(String path, String text) throws IOException;

}
