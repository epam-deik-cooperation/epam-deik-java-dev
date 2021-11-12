package com.epam.training.webshop.service;

import com.epam.training.webshop.model.Product;

import java.util.List;

public interface WarehouseService extends Observer {

    void registerOrderedProducts(List<Product> products);
}
