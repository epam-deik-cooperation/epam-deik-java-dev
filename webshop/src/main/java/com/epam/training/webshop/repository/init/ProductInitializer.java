package com.epam.training.webshop.repository.init;

import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("! prod")
public class ProductInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInitializer.class);

    private static final List<Product> PRODUCTS = List.of(
            new Product("Alma", 100),
            new Product("Körte", 200),
            new Product("Barack", 300),
            Product.builder().withName("Dinnye").withValue(400).build()
    );

    private ProductRepository productRepository;

    public ProductInitializer(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initProducts() {
        LOGGER.info("Init products...");
        PRODUCTS.forEach(productRepository::save);
        LOGGER.info("Product initialization is finished");
        productRepository.findAll().forEach(product -> LOGGER.info("Saved product: {}", product));
    }

}
