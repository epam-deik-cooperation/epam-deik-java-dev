package com.epam.training.webshop.cart;

import java.util.Currency;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.training.webshop.finance.bank.impl.BankImpl;
import com.epam.training.webshop.finance.bank.model.CurrencyPair;
import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.product.Product;

class CartTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final CurrencyPair HUF_TO_HUF_CURRENCY_PAIR = new CurrencyPair(HUF_CURRENCY, HUF_CURRENCY);
    private static final CurrencyPair USD_TO_HUF_CURRENCY_PAIR = new CurrencyPair(USD_CURRENCY, HUF_CURRENCY);
    private static final Product TEST_PRODUCT_MILK = new Product("tej", new Money(500, HUF_CURRENCY));
    private static final Product TEST_PRODUCT_BREAD = new Product("kenyér", new Money(600, HUF_CURRENCY));
    private static final Product TEST_PRODUCT_CHOCOLATE = new Product("csoki", new Money(1.5, USD_CURRENCY));

    private final BankImpl mockBank = Mockito.mock(BankImpl.class);

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
        Money actual = underTest.getAggregatedNetPrice(HUF_CURRENCY);

        // Then
        Assertions.assertEquals(zero, actual);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenOneItemInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK);
        Mockito.when(mockBank.getRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        // could be named expected instead of fiveHundred
        Money fiveHundred = new Money(500, Currency.getInstance("HUF"));

        // When
        Money actual = underTest.getAggregatedNetPrice(HUF_CURRENCY);

        // Then
        Assertions.assertEquals(fiveHundred, actual);
        Mockito.verify(mockBank).getRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenTwoItemsInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_BREAD);
        Mockito.when(mockBank.getRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Money expected = new Money(1100, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice(HUF_CURRENCY);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank, Mockito.times(2)).getRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnACorrectPriceWhenTwoItemsInTheCartWithDifferentCurrencies() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_CHOCOLATE);
        Mockito.when(mockBank.getRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Mockito.when(mockBank.getRate(USD_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(300D));
        Money expected = new Money(950, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice(HUF_CURRENCY);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank).getRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verify(mockBank).getRate(USD_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }

    @Test
    public void testGetAggregatedNetPriceShouldReturnACorrectPriceWhenItemIsDuplicatedInTheCart() {
        // Given
        Cart underTest = Cart.of(mockBank, TEST_PRODUCT_MILK, TEST_PRODUCT_MILK);
        Mockito.when(mockBank.getRate(HUF_TO_HUF_CURRENCY_PAIR)).thenReturn(Optional.of(1D));
        Money expected = new Money(1000, HUF_CURRENCY);

        // When
        Money actual = underTest.getAggregatedNetPrice(HUF_CURRENCY);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockBank, Mockito.times(2)).getRate(HUF_TO_HUF_CURRENCY_PAIR);
        Mockito.verifyNoMoreInteractions(mockBank);
    }


}