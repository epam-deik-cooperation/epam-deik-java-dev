package com.epam.training.webshop.core.checkout.model;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.List;
import lombok.Value;

@Value
public class Order {

    private final List<Product> productList;
    private final Money netPrice;
    private final Money grossPrice;
}
