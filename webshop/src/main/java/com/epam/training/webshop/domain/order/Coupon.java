package com.epam.training.webshop.domain.order;

import com.epam.training.webshop.domain.order.model.Product;
import java.util.List;

public interface Coupon {

    String getId();

    double getDiscountForProducts(List<Product> products);
}

