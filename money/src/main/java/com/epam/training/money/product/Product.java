package com.epam.training.money.product;

import com.epam.training.money.Money;
import lombok.Value;

@Value
public class Product {

    private final String name;
    private final Money netPrice;
}
