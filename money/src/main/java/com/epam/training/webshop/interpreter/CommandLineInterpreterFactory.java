package com.epam.training.webshop.interpreter;

import com.epam.training.webshop.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.cart.grossprice.impl.GrossPriceCalculatorImpl;
import com.epam.training.webshop.cart.grossprice.impl.HungarianTaxGrossPriceDecorator;
import com.epam.training.webshop.command.AbstractCommand;
import com.epam.training.webshop.command.UserCartAddProductCommand;
import com.epam.training.webshop.command.UserCartCheckoutCommand;
import com.epam.training.webshop.command.UserCartClearCommand;
import com.epam.training.webshop.command.UserCartListCommand;
import com.epam.training.webshop.command.UserCartRemoveProductCommand;
import com.epam.training.webshop.command.UserProductListCommand;
import com.epam.training.webshop.product.ProductService;
import com.epam.training.webshop.product.ProductServiceImpl;
import java.util.Set;

public class CommandLineInterpreterFactory {

    public static CommandLineInterpreter create() {
        ProductService productService = new ProductServiceImpl();
        GrossPriceCalculator grossPriceCalculator = new HungarianTaxGrossPriceDecorator(new GrossPriceCalculatorImpl());
        Set<AbstractCommand> commandSet = Set.of(
            new UserProductListCommand(productService),
            new UserCartAddProductCommand(productService),
            new UserCartRemoveProductCommand(productService),
            new UserCartCheckoutCommand(grossPriceCalculator),
            new UserCartListCommand(),
            new UserCartClearCommand()
        );
        return new CommandLineInterpreter(commandSet);
    }
}
