package com.epam.training.webshop.repository.impl;

import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.order.model.impl.SimpleProduct;
import com.epam.training.webshop.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class DummyProductRepository implements ProductRepository {

    @Value("${products.apple.name}")
    private String appleName;

    @Value("${products.apple.price}")
    private Double applePrice;

    @Value("${products.brandy.name:Alcoholic drink}")
    private String brandyName;

    @Value("${products.brandy.price:999}")
    private Double brandyPrice;

    @Value("#{'${products.caviar}'.split(',')}")
    private List<String> caviarDetails;

    @Override
    public List<Product> getAllProduct() {
        return List.of(
                new SimpleProduct(appleName, applePrice),
                new SimpleProduct(brandyName, brandyPrice),
                SimpleProduct.builder()
                        .withName(caviarDetails.get(0))
                        .withValue(Double.parseDouble(caviarDetails.get(1)))
                        .build()
        );
    }
}
