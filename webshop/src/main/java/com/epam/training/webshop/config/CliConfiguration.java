package com.epam.training.webshop.config;

import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.domain.grossprice.impl.HungarianTaxGrossPriceCalculator;
import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.impl.CartImpl;
import com.epam.training.webshop.domain.order.orderconfirm.OrderConfirmationService;
import com.epam.training.webshop.domain.order.orderconfirm.impl.DummyOrderConfirmationService;
import com.epam.training.webshop.domain.warehouse.Warehouse;
import com.epam.training.webshop.domain.warehouse.impl.DummyWarehouse;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.AddProductCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.ExitCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.OrderCommandLineParser;
import com.epam.training.webshop.repository.OrderRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.repository.impl.DummyOrderRepository;
import com.epam.training.webshop.repository.impl.DummyProductRepository;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public final class CliConfiguration {

    private CliConfiguration() {
    }

    public static GrossPriceCalculatorDecorator hungarianTaxGrossPriceCalculator() {
        return new HungarianTaxGrossPriceCalculator(GrossPriceCalculatorSingleton.getGrossPriceCalculator());
    }

    public static CliInterpreter cliInterpreter() {
        Cart cart = cart();
        CliInterpreter cliInterpreter = new CliInterpreter(cliReader(), cliWriter());
        CommandLineParser commandLineParserChain = new ExitCommandLineParser(cliInterpreter);
        commandLineParserChain.setSuccessor(new AddProductCommandLineParser(productRepository(), cart));
        commandLineParserChain.setSuccessor(new OrderCommandLineParser(cart));
        cliInterpreter.updateCommandLineParser(commandLineParserChain);
        return cliInterpreter;
    }

    public static Writer cliWriter() {
        return new OutputStreamWriter(System.out);
    }

    public static Reader cliReader() {
        return new InputStreamReader(System.in);
    }

    public static Cart cart() {
        CartImpl cart = new CartImpl(orderRepository(), hungarianTaxGrossPriceCalculator());
        cart.subscribe(warehouse());
        cart.subscribe(orderConfirmationService());
        return cart;
    }

    public static ProductRepository productRepository() {
        return new DummyProductRepository();
    }

    public static OrderRepository orderRepository() {
        return new DummyOrderRepository();
    }

    public static Warehouse warehouse() {
        return new DummyWarehouse();
    }

    public static OrderConfirmationService orderConfirmationService() {
        return new DummyOrderConfirmationService();
    }
}
