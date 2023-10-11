package com.epam.training.webshop.ui.session;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.StaticBank;
import lombok.Getter;

public enum Session {

    INSTANCE;

    private final Bank bank = new StaticBank();

    @Getter
    private final Cart cart = Cart.createEmptyCart(bank);
}
