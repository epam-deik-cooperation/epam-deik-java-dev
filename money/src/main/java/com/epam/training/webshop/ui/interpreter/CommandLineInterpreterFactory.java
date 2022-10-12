package com.epam.training.webshop.ui.interpreter;

import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.cart.grossprice.impl.GrossPriceCalculatorImpl;
import com.epam.training.webshop.core.cart.grossprice.impl.HungarianTaxGrossPriceDecorator;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.ProductServiceImpl;
import com.epam.training.webshop.ui.command.AbstractCommand;
import com.epam.training.webshop.ui.command.UserCartAddProductCommand;
import com.epam.training.webshop.ui.command.UserCartCheckoutCommand;
import com.epam.training.webshop.ui.command.UserCartClearCommand;
import com.epam.training.webshop.ui.command.UserCartListCommand;
import com.epam.training.webshop.ui.command.UserCartRemoveProductCommand;
import com.epam.training.webshop.ui.command.UserProductListCommand;
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
