package com.epam.training.webshop.core.cart;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Cart implements CheckoutObserver {

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

    public void removeProduct(Product product) {
        if (product != null) {
            productList.remove(product);
        }
    }

    public Money getAggregatedNetPrice() {
        Money aggregatedPrice = new Money(0, Currency.getInstance("HUF"));
        for (Product product : productList) {
            aggregatedPrice = aggregatedPrice.add(product.getNetPrice(), bank);
        }
        return aggregatedPrice;
    }

    public List<Product> getProductList() {
        return List.copyOf(productList);
    }

    public void clearCart() {
        productList.clear();
    }

    @Override
    public void handleOrder(Order order) {
        clearCart();
    }
}
