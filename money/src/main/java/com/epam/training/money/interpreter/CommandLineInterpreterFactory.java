package com.epam.training.money.interpreter;

import com.epam.training.money.command.AbstractCommand;
import com.epam.training.money.command.UserCartAddProductCommand;
import com.epam.training.money.command.UserCartCheckoutCommand;
import com.epam.training.money.command.UserCartClearCommand;
import com.epam.training.money.grosspricecalculator.GrossPriceCalculator;
import com.epam.training.money.grosspricecalculator.GrossPriceCalculatorImpl;
import com.epam.training.money.grosspricecalculator.HUGrossPriceCalculatorDecorator;
import com.epam.training.money.product.ProductService;
import com.epam.training.money.product.ProductServiceImpl;
import java.util.List;

public class CommandLineInterpreterFactory {

  public static CommandLineInterpreter createInterpreter() {
    GrossPriceCalculator grossPriceCalculator = new HUGrossPriceCalculatorDecorator(
        new GrossPriceCalculatorImpl());

    ProductService productService = new ProductServiceImpl();

    List<AbstractCommand> abstractCommands = List.of(
        new UserCartAddProductCommand(productService),
        new UserCartCheckoutCommand(grossPriceCalculator),
        new UserCartClearCommand()
    );
    return new CommandLineInterpreter(abstractCommands);
  }

}
