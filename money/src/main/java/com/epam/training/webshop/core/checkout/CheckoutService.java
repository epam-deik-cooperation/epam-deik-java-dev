package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.model.Order;

public interface CheckoutService {

    Order checkout(Cart cart);
}
