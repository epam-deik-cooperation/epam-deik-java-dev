package com.epam.training.webshop.core.configuration;

import com.epam.training.webshop.core.product.persistence.entity.Product;
import com.epam.training.webshop.core.product.persistence.repository.ProductRepository;
import com.epam.training.webshop.core.user.persistence.entity.User;
import com.epam.training.webshop.core.user.persistence.repository.UserRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class InMemoryDatabaseInitializer {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public InMemoryDatabaseInitializer(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        Product videoCard = new Product("GPU", 600_000, "HUF");
        Product playStation5 = new Product("PS5", 500_000, "HUF");
        productRepository.saveAll(List.of(videoCard, playStation5));

        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
    }
}
