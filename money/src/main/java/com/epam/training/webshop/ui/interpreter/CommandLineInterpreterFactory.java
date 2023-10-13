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
        GrossPriceCalculator calculator = new HungarianTaxGrossPriceDecorator(new GrossPriceCalculatorImpl());
        ProductService productService = new ProductServiceImpl();
        Set<AbstractCommand> abstractCommands = Set.of(
            new UserCartListCommand(),
            new UserCartCheckoutCommand(calculator),
            new UserCartAddProductCommand(productService),
            new UserCartRemoveProductCommand(productService),
            new UserCartClearCommand(),
            new UserProductListCommand(productService)
        );
        return new CommandLineInterpreter(abstractCommands);
    }
}
