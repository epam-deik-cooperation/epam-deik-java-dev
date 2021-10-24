package com.epam.training.taxi.account;

public final class RegisteredAccount implements Account{

    private final String accountId;
    private final String fullName;
    private final Double discountPercentage;

    public RegisteredAccount(String accountId, String fullName, Double discountPercentage) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.discountPercentage = discountPercentage;
    }

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
