package com.epam.training.webshop.config;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorImpl;
import com.epam.training.webshop.domain.grossprice.impl.HungarianTaxGrossPriceCalculator;
import com.epam.training.webshop.domain.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.impl.CartImpl;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.AddProductCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.ExitCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.OrderCommandLineParser;
import com.epam.training.webshop.repository.OrderRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.repository.impl.DummyOrderRepository;
import com.epam.training.webshop.repository.impl.DummyProductRepository;

@Configuration
public class WebShopConfiguration {

    @Bean
    public CliInterpreter cliInterpreter(Reader reader, Writer writer,  @Lazy CommandLineParser commandLineParser) {
        CliInterpreter cliInterpreter = new CliInterpreter(reader, writer);
        cliInterpreter.updateCommandLineParser(commandLineParser);
        return cliInterpreter;
    }

    @Bean
    public Writer cliWriter() {
        return new OutputStreamWriter(System.out);
    }

    @Bean
    public Reader cliReader() {
        return new InputStreamReader(System.in);
    }

    @Bean
    public CommandLineParser commandLineParser(CliInterpreter cliInterpreter, ShoppingCartService shoppingCartService) {
        CommandLineParser commandLineParserChain = new ExitCommandLineParser(cliInterpreter);
        commandLineParserChain.setSuccessor(new AddProductCommandLineParser(shoppingCartService));
        commandLineParserChain.setSuccessor(new OrderCommandLineParser(shoppingCartService));
        return commandLineParserChain;
    }

//    @Bean
//    public ShoppingCartService shoppingCartService(Cart cart, ProductRepository productRepository) {
//        return new ShoppingCartServiceImpl(cart, productRepository);
//    }

    @Bean
    public Cart cart(OrderRepository orderRepository, GrossPriceCalculator grossPriceCalculator) {
        return new CartImpl(orderRepository, grossPriceCalculator);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new DummyOrderRepository();
    }

    @Bean
    public ProductRepository productRepository() {
        return new DummyProductRepository();
    }

    @Bean
    @Primary
    public GrossPriceCalculator hungarianTaxGrossPriceCalculator(
            @Qualifier("defaultGrossPriceCalculator") GrossPriceCalculator grossPriceCalculator) {
        return new HungarianTaxGrossPriceCalculator(grossPriceCalculator);
    }

    @Bean
    public GrossPriceCalculator defaultGrossPriceCalculator() {
        return new GrossPriceCalculatorImpl();
    }

}
