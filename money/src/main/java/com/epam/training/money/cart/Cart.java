package com.epam.training.money.cart;

import com.epam.training.money.Money;
import com.epam.training.money.bank.Bank;
import com.epam.training.money.product.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart {

    private final Bank bank;
    private final List<Product> productList;

    public static Cart createEmptyCart(Bank bank) {
        return new Cart(bank, new ArrayList<>());
    }

    public static Cart createCart(Bank bank, Product... products) {
        return new Cart(bank, Arrays.asList(products));
    }

    public void addProduct(Product product) {
        if (product != null) {
            productList.add(product);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Money getAggregatedNetPrice() {
        Money aggregatedPrice = new Money(0, Currency.getInstance("HUF"));
        for (Product product : productList) {
            aggregatedPrice = aggregatedPrice.add(product.getNetPrice(), bank);
        }
        return aggregatedPrice;
    }
}
