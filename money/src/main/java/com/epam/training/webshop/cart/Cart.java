package com.epam.training.webshop.cart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

import com.epam.training.webshop.finance.bank.impl.BankImpl;
import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.product.Product;

public class Cart {

    private final List<Product> productList;
    private final BankImpl bank;

    public static Cart of(BankImpl bank, Product... products) {
        return new Cart(bank, Arrays.asList(products));
    }

    public Cart(BankImpl bank) {
        this(bank, new ArrayList<>());
    }

    Cart(BankImpl bank, List<Product> productList) {
        this.bank = bank;
        this.productList = productList;
    }

    public void add(Product product) {
        productList.add(product);
    }

    public Money getAggregatedNetPrice(Currency targetCurrency) {
        Money aggregatedPrice = new Money(0, targetCurrency);

        for (Product product : productList) {
            aggregatedPrice = aggregatedPrice.add(product.getNetPrice(), bank);
        }
        return aggregatedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(productList, cart.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productList);
    }

}
