package com.epam.training.webshop.config;

import com.epam.training.webshop.domain.ShoppingCartService;
import com.epam.training.webshop.domain.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.domain.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import com.epam.training.webshop.repository.OrderRepository;
import com.epam.training.webshop.repository.ProductRepository;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.CollectionUtils;

@Configuration
@ComponentScan("com.epam.training.webshop")
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class WebShopConfiguration {

    @Bean
    public CliInterpreter cliInterpreter(Reader reader, Writer writer, @Lazy CommandLineParser commandLineParser) {
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
    public CommandLineParser commandLineParser(CliInterpreter cliInterpreter, ShoppingCartService shoppingCartService, List<CommandLineParser> commandLineParsers) {
        // Példák különböző konfigurációs megközelítésekre
        //        CommandLineParser commandLineParserChain = new ExitCommandLineParser(cliInterpreter);
        //        commandLineParserChain.setSuccessor(new AddProductCommandLineParser(shoppingCartService));
        //        commandLineParserChain.setSuccessor(new OrderCommandLineParser(shoppingCartService));
        final LinkedList<CommandLineParser> parsers = new LinkedList<>(commandLineParsers);
        final CommandLineParser firstParser = parsers.getFirst();
        CommandLineParser actualParser = parsers.removeFirst();
        while (!CollectionUtils.isEmpty(parsers)) {
            final CommandLineParser nextParser = parsers.removeFirst();
            actualParser.setSuccessor(nextParser);
            actualParser = nextParser;
        }
        return firstParser;
    }

    @Bean
    public ShoppingCartService shoppingCartService(Cart cart,
                                                   ProductRepository productRepository,
                                                   OrderRepository orderRepository,
                                                   @Qualifier("hungarianTaxGrossPriceCalculator") GrossPriceCalculator grossPriceCalculator,
                                                   List<Observer> observers) {
        final ShoppingCartServiceImpl shoppingCartService = new ShoppingCartServiceImpl(cart, productRepository, orderRepository, grossPriceCalculator);
        observers.forEach(shoppingCartService::subscribe);
        return shoppingCartService;
    }
}
