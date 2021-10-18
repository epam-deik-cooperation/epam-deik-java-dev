package com.epam.training.webshop.repository.impl;

import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.order.model.impl.SimpleProduct;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.List;


public class DummyProductRepository implements ProductRepository {
    @Override
    public List<Product> getAllProduct() {
        return List.of(
                new SimpleProduct("Alma", 100),
                new SimpleProduct("Pálinka", 1000),
                SimpleProduct.builder()
                        .withName("Kaviár")
                        .withValue(10_000)
                        .build()
        );
    }
}
