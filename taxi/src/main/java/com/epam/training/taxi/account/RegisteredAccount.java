package com.epam.training.taxi.account;

import java.util.Objects;

public final class RegisteredAccount implements Account {

    private final Long accountId;
    private final String fullName;
    private final Double discountPercentage;

    public RegisteredAccount(Long accountId, String fullName, Double discountPercentage) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public Long getAccountId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegisteredAccount that = (RegisteredAccount) o;

        return Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return accountId != null ? accountId.hashCode() : 0;
    }
}
