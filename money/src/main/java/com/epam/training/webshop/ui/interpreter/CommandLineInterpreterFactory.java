package com.epam.training.webshop.ui.interpreter;

import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.cart.grossprice.impl.GrossPriceCalculatorImpl;
import com.epam.training.webshop.core.cart.grossprice.impl.HungarianTaxGrossPriceCalculator;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.ProductServiceImpl;
import com.epam.training.webshop.ui.command.impl.AbstractCommand;
import com.epam.training.webshop.ui.command.impl.UserAddProductToCartCommand;
import com.epam.training.webshop.ui.command.impl.UserCheckoutCartCommand;
import com.epam.training.webshop.ui.command.impl.UserProductListCommand;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class CommandLineInterpreterFactory {

    public static CommandLineInterpreter create(InputStream inputStream, OutputStream outputStream) {
        GrossPriceCalculator calculator = new HungarianTaxGrossPriceCalculator(new GrossPriceCalculatorImpl());
        ProductService grossPriceCalculator = new ProductServiceImpl();
        Set<AbstractCommand> abstractCommands = Set.of(
            new UserCheckoutCartCommand(calculator),
            new UserProductListCommand(grossPriceCalculator),
            new UserAddProductToCartCommand(grossPriceCalculator)
        );
        return new CommandLineInterpreter(inputStream, outputStream, abstractCommands);
    }
}
