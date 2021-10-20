package com.epam.training.webshop.ui.session;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.finance.bank.impl.StaticBank;

public enum Session {

    INSTANCE;

    private final Cart cart = new Cart(new StaticBank());

    public Cart getCart() {
        return cart;
    }
}
