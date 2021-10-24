package com.epam.training.taxi.invoice;

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
}
