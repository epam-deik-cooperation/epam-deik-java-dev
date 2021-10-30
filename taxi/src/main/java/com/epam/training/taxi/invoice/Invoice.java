package com.epam.training.taxi.invoice;

public interface Invoice {

    Long getAccountId();

    Double getDistance();

    Double getPrice();

    Double getDiscountAmount();

}
