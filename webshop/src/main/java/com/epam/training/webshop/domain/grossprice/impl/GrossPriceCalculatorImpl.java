package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import org.springframework.stereotype.Component;

@Component
public class GrossPriceCalculatorImpl implements GrossPriceCalculator {

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return cartService.getTotalNetPrice();
    }
}
