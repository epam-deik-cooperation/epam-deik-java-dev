package com.epam.training.taxi.invoice;

import java.util.Objects;

public final class SimpleInvoice implements Invoice {

    private final Long accountId;
    private final Double distance;
    private final Double price;
    private final Double discountAmount;

    public SimpleInvoice(Long accountId, Double distance, Double price, Double discountAmount) {
        this.accountId = accountId;
        this.distance = distance;
        this.price = price;
        this.discountAmount = discountAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getPrice() {
        return price;
    }

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
        if (!Objects.equals(distance, simpleInvoice.distance)) {
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
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        return result;
    }
}
