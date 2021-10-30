package com.epam.training.taxi.invoice;

public interface Invoice {

    Long getAccountId();

    Double getDistanceTravelled();

    Double getPrice();

    Double getDiscountAmount();

}
