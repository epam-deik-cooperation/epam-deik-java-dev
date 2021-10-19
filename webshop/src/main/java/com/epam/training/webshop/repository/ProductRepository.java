package com.epam.training.webshop.repository;

import com.epam.training.webshop.domain.order.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getAllProduct();
}
