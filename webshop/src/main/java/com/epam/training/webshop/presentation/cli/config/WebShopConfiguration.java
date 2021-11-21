package com.epam.training.webshop.presentation.cli.config;

import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.repository.CartRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.service.GrossPriceCalculator;
import com.epam.training.webshop.service.Observer;
import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.impl.ShoppingCartServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebShopConfiguration {

    @Bean
    public ShoppingCartService shoppingCartService(Cart cart,
                                                   ProductRepository productRepository,
                                                   CartRepository cartRepository,
                                                   @Qualifier("hungarianTaxGrossPriceCalculator") GrossPriceCalculator grossPriceCalculator,
                                                   List<Observer> observers) {
        final ShoppingCartServiceImpl shoppingCartService = new ShoppingCartServiceImpl(cart, productRepository, cartRepository, grossPriceCalculator);
        observers.forEach(shoppingCartService::subscribe);
        return shoppingCartService;
    }
}
