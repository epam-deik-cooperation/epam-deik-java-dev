package com.epam.training.webshop.ui.session;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.finance.bank.Bank;

public enum Session {

    INSTANCE;

    private final Cart cart = Cart.createEmptyCart(new Bank());

    public Cart getCart() {
        return cart;
    }
}