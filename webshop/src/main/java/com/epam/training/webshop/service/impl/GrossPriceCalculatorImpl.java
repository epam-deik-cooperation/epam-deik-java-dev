package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.GrossPriceCalculator;
import org.springframework.stereotype.Component;

@Component
public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return cartService.getTotalNetPrice();
    }
}
