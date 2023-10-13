package com.epam.training.money.session;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.bank.StaticBank;
import com.epam.training.money.cart.Cart;
import lombok.Getter;

public enum Session {

    INSTANCE;

    private final Bank bank = new StaticBank();

    @Getter
    private final Cart cart = Cart.createEmptyCart(bank);
}
