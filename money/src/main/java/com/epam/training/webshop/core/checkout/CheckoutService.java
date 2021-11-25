package com.epam.training.webshop.core.checkout;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.model.OrderDto;

public interface CheckoutService {

    OrderDto checkout(Cart cart);
}
