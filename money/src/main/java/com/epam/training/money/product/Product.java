package com.epam.training.money.product;

import com.epam.training.money.impl.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {
    private String name;
    private Money netPrice;
}
