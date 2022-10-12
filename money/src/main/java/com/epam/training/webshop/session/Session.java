package com.epam.training.webshop.session;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.finance.bank.Bank;

public enum Session {

    INSTANCE;

    private final Cart cart = Cart.createEmptyCart(new Bank());

    public Cart getCart() {
        return cart;
    }
}