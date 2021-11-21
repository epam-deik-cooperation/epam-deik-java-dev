package com.epam.training.webshop.repository;

import com.epam.training.webshop.model.Product;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, Long> {

    List<Product> findAll();

    void save(Product product);
}
