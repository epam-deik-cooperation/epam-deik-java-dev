package com.epam.training.webshop.domain.warehouse;

import com.epam.training.webshop.domain.order.Observer;
import com.epam.training.webshop.domain.order.model.Product;
import java.util.List;

public interface Warehouse extends Observer {

    void registerOrderedProducts(List<Product> products);
}
