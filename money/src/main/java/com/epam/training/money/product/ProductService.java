package com.epam.training.money.product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProductList();

    Optional<Product> getProductByName(String name);
}
