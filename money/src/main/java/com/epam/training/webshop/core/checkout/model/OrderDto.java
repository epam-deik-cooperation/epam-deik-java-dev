package com.epam.training.webshop.core.checkout.model;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.Map;

public record OrderDto(Map<ProductDto, Integer> productMap, Money netPrice, Money grossPrice) {
}
