package com.epam.training.webshop.config;

import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorImpl;

public final class GrossPriceCalculatorSingleton {

    // private static GrossPriceCalculator grossPriceCalculatorDecorator = new GrossPriceCalculatorImpl();  // example for EagerInitializedSingleton
    private static GrossPriceCalculator grossPriceCalculatorDecorator;

    private GrossPriceCalculatorSingleton() {
    }

    // example for EagerInitializedSingleton
    //        static {
    //            grossPriceCalculatorDecorator = new GrossPriceCalculatorImpl();
    //        }

    public static GrossPriceCalculator getGrossPriceCalculator() {
        if (grossPriceCalculatorDecorator == null) { // LazyInitializedSingleton
            grossPriceCalculatorDecorator = new GrossPriceCalculatorImpl();
        }
        return grossPriceCalculatorDecorator;
    }
}
