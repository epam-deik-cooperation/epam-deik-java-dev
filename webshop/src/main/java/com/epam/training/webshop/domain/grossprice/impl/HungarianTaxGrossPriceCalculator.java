package com.epam.training.webshop.domain.grossprice.impl;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HungarianTaxGrossPriceCalculator extends GrossPriceCalculatorDecorator {

    private final double taxRate;

    public HungarianTaxGrossPriceCalculator(GrossPriceCalculator grossPriceCalculator, @Value("${price.tax-rate.hu}") double taxRate) {
        super(grossPriceCalculator);
        this.taxRate = taxRate;
    }

    @Override
    public double getAggregatedGrossPrice(ShoppingCartService cartService) {
        return super.getAggregatedGrossPrice(cartService) * taxRate;
    }
}
