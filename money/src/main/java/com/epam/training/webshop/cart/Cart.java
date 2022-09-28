package com.epam.training.webshop.cart;

import com.epam.training.webshop.finance.bank.Bank;
import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.product.Product;
import java.util.Currency;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Cart {

    private Bank bank;
    private List<Product> productList;

    public void addProduct(Product product) {
        if (product != null) {
            productList.add(product);
        }
    }

    public Money getAggregatedNetPrice(Currency targetCurrency) {
        Money aggregatedPrice = new Money(0, targetCurrency);
        for (Product product : productList) {
            aggregatedPrice = aggregatedPrice.add(product.getNetPrice(), bank);
        }
        return aggregatedPrice;
    }
}
