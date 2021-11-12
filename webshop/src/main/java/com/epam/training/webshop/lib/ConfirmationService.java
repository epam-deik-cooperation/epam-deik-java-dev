package com.epam.training.webshop.lib;

import com.epam.training.webshop.model.Product;

import java.util.List;

public interface ConfirmationService {

    void sendConfirmationMessageAbout(List<Product> products);
}
