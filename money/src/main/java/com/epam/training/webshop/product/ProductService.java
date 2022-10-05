package com.epam.training.webshop.product;

import com.epam.training.webshop.product.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProductList();

    Optional<Product> getProductByName(String productName);
}
