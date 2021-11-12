package com.epam.training.webshop.repository.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.repository.ProductRepository;

@Component
public class ProductInitializer {

    private static List<Product> products = List.of(
            new Product("Alma", 100)
    );

    private ProductRepository repository;

    @PostConstruct
    public void initProducts() {
        products.forEach(repository::save);
    }

}
