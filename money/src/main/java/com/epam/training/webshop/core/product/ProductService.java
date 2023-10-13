package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.product.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProductList();

    Optional<Product> getProductByName(String name);
}
