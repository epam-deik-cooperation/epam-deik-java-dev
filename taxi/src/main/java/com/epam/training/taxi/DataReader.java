package com.epam.training.taxi;

import java.util.List;
import java.util.Optional;

public interface DataReader {
    List<String[]> getAllData();

    Optional<String[]> getDataByAccountId(String accountId);
}
