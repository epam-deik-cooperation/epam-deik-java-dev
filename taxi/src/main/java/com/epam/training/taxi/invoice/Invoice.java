package com.epam.training.taxi.invoice;

import java.util.Objects;

public final class Invoice {

    private String accountId;
    private Double distance;
    private Double price;
    private Double discountAmount;

    public Invoice(String accountId, Double distance, Double price, Double discountAmount) {
        this.accountId = accountId;
        this.distance = distance;
        this.price = price;
        this.discountAmount = discountAmount;
    }

    public String getAccountId() {
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

        Invoice invoice = (Invoice) o;

        if (!Objects.equals(accountId, invoice.accountId)) {
            return false;
        }
        if (!Objects.equals(distance, invoice.distance)) {
            return false;
        }
        if (!Objects.equals(price, invoice.price)) {
            return false;
        }
        return Objects.equals(discountAmount, invoice.discountAmount);
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "accountId='" + accountId + '\'' +
                ", distance=" + distance +
                ", price=" + price +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
