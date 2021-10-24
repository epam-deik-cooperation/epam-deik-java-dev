package com.epam.training.taxi.account;

public final class UnregisteredAccount implements Account {

    @Override
    public String getAccountId() {
        return "null";
    }

    @Override
    public String getFullName() {
        return "Unknown";
    }

    @Override
    public Double getDiscountPercentage() {
        return 0.0;
    }
}
