package com.epam.training.webshop.core.cart;

import com.epam.training.webshop.core.checkout.CheckoutObserver;
import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Cart implements CheckoutObserver {

    private final List<ProductDto> productList;
    private final Bank bank;

    public Cart(Bank bank) {
        this(bank, new ArrayList<>());
    }

    Cart(Bank bank, List<ProductDto> productList) {
        this.bank = bank;
        this.productList = productList;
    }

    public static Cart of(Bank bank, ProductDto... products) {
        return new Cart(bank, new LinkedList<>(Arrays.asList(products)));
    }

    public void add(ProductDto product) {
        productList.add(product);
    }

    public List<ProductDto> getProductList() {
        return List.copyOf(productList);
    }

    public Money getAggregatedNetPrice() {
        Money aggregatedPrice = new Money(0, Currency.getInstance("HUF"));
        for (ProductDto product : productList) {
            aggregatedPrice = aggregatedPrice.add(product.getNetPrice(), bank);
        }
        return aggregatedPrice;
    }

    @Override
    public void handleOrder(Order order) {
        productList.clear();
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
