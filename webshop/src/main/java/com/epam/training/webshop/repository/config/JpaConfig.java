package com.epam.training.webshop.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.epam.training.webshop.repository")
@EntityScan("com.epam.training.webshop.model")
@EnableTransactionManagement
public class JpaConfig {
}
