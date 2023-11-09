package com.epam.training.webshop.core.configuration;

import com.epam.training.webshop.core.product.persistence.Product;
import com.epam.training.webshop.core.product.persistence.ProductRepository;
import com.epam.training.webshop.core.user.persistence.User;
import com.epam.training.webshop.core.user.persistence.UserRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

        Product product = new Product("Malac", 100.0, "HUF");
        productRepository.save(product);
    }
}
