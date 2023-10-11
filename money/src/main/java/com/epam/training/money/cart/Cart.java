package com.epam.training.money.cart;

import com.epam.training.money.Money;
import com.epam.training.money.bank.Bank;
import com.epam.training.money.product.Product;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cart {

    private final Bank bank;

    @Getter
    private final Map<Product, Integer> productMap;

    public static Cart createEmptyCart(Bank bank) {
        return new Cart(bank, new HashMap<>());
    }

    public void addProduct(Product product, int amount) {
        if (product != null && amount > 0) {
            productMap.merge(product, amount, Integer::sum);
        }
    }

    public void removeProduct(Product product) {
        productMap.remove(product);
    }

    public boolean containsProduct(Product product) {
        return productMap.containsKey(product);
    }

    public void clear() {
        productMap.clear();
    }

    public boolean isEmpty() {
        return productMap.isEmpty();
    }

    public Money getAggregatedNetPrice() {
        Money aggregatedPrice = new Money(0, Currency.getInstance("HUF"));
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            aggregatedPrice = aggregatedPrice.add(entry.getKey().getNetPrice().multiply(entry.getValue()), bank);
        }
        return aggregatedPrice;
    }
}
