package com.epam.training.taxi.invoice;

import java.util.Objects;

public final class SimpleInvoice implements Invoice {

    private final Long accountId;
    private final Double distanceTravelled;
    private final Double price;
    private final Double discountAmount;

    public SimpleInvoice(Long accountId, Double distanceTravelled, Double price, Double discountAmount) {
        this.accountId = accountId;
        this.distanceTravelled = distanceTravelled;
        this.price = price;
        this.discountAmount = discountAmount;
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public Double getDistanceTravelled() {
        return distanceTravelled;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public Double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleInvoice simpleInvoice = (SimpleInvoice) o;

        if (!Objects.equals(accountId, simpleInvoice.accountId)) {
            return false;
        }
        if (!Objects.equals(distanceTravelled, simpleInvoice.distanceTravelled)) {
            return false;
        }
        if (!Objects.equals(price, simpleInvoice.price)) {
            return false;
        }
        return Objects.equals(discountAmount, simpleInvoice.discountAmount);
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (distanceTravelled != null ? distanceTravelled.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        return result;
    }
}
