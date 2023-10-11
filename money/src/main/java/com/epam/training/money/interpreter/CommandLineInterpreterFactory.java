package com.epam.training.money.interpreter;

import com.epam.training.money.command.AbstractCommand;
import com.epam.training.money.command.UserCartAddProductCommand;
import com.epam.training.money.command.UserCartCheckoutCommand;
import com.epam.training.money.command.UserCartClearCommand;
import com.epam.training.money.command.UserCartListCommand;
import com.epam.training.money.command.UserCartRemoveProductCommand;
import com.epam.training.money.command.UserProductListCommand;
import com.epam.training.money.grossprice.GrossPriceCalculator;
import com.epam.training.money.grossprice.impl.GrossPriceCalculatorImpl;
import com.epam.training.money.grossprice.impl.HungarianTaxGrossPriceDecorator;
import com.epam.training.money.product.ProductService;
import com.epam.training.money.product.ProductServiceImpl;
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
