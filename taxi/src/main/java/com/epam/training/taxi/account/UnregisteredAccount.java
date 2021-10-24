package com.epam.training.taxi.account;

public final class UnregisteredAccount implements Account{

    private final String accountId = "null";
    private final String fullName = "Unknown";
    private final Double discountPercentage = 0.0;

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public Double getDiscountPercentage() {
        return discountPercentage;
    }
}
