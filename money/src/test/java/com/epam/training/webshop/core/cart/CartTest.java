package com.epam.training.webshop.core.cart;

import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CartTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final CurrencyPair HUF_TO_HUF_CURRENCY_PAIR = new CurrencyPair(HUF_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair USD_TO_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final ProductDto TEST_PRODUCT_MILK = new ProductDto("tej", new Money(500, HUF_CURRENCY));
    private static final ProductDto TEST_PRODUCT_BREAD = new ProductDto("kenyér", new Money(600, HUF_CURRENCY));
    private static final ProductDto TEST_PRODUCT_CHOCOLATE = new ProductDto("csoki", new Money(1.5, USD_CURRENCY));

    private final Bank mockBank = Mockito.mock(Bank.class);

    @Test
    public void testAddShouldStoreProductWhenAddingProductToTheCart() {
        // Given
        Cart underTest = new Cart(mockBank);
        Cart expected = Cart.of(mockBank, TEST_PRODUCT_MILK);
        // When
        underTest.add(TEST_PRODUCT_MILK);

        // Then
        Assertions.assertEquals(expected, underTest);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnZeroPriceWhenNoItemsAreInTheCart() {
        // Given
        Cart underTest = new Cart(mockBank);
        // could be named expected instead of zero
        Money zero = new Money(0D, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice();

        // Then
        Assertions.assertEquals(zero, actual);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenOneItemInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK);
        Mockito.when(mockBank.getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        // could be named expected instead of fiveHundred
        Money fiveHundred = new Money(500, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice();

        // Then
        Assertions.assertEquals(fiveHundred, actual);
        Mockito.verify(mockBank).getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenTwoItemsInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_BREAD);
        Mockito.when(mockBank.getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Money expected = new Money(1100, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank, Mockito.times(2)).getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnACorrectPriceWhenTwoItemsInTheCartWithDifferentCurrencies() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_CHOCOLATE);
        Mockito.when(mockBank.getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Mockito.when(mockBank.getExchangeRate(USD_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(300D));
        Money expected = new Money(950, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank).getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verify(mockBank).getExchangeRate(USD_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnACorrectPriceWhenItemIsDuplicatedInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_MILK);
        Mockito.when(mockBank.getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Money expected = new Money(1000, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank, Mockito.times(2)).getExchangeRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testHandleOrderShouldEmptyTheProductListWhenItIsCalled() {
        // Given
        Order order = Mockito.mock(Order.class);
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_CHOCOLATE);
        Cart expected = Cart.of(mockBank);

        // When
        underTest.handleOrder(order);

        // Then
        Assertions.assertEquals(expected, underTest);
        Mockito.verifyNoMoreInteractions(mockBank, order);
    }
}