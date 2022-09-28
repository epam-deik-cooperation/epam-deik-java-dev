package com.epam.training.webshop.product;

import com.epam.training.webshop.finance.money.Money;
import lombok.Value;

@Value
public class Product {

    private final String name;
    private final Money netPrice;
}
