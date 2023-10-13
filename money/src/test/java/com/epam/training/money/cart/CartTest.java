package com.epam.training.money.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.money.Money;
import com.epam.training.money.bank.Bank;
import com.epam.training.money.product.Product;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CartTest {

    private static final Product HUF_PRODUCT = Product.builder()
        .withName("diploma")
        .withNetPrice(new Money(5000, Currency.getInstance("HUF")))
        .build();
    private static final Product USD_PRODUCT = Product.builder()
        .withName("diploma")
        .withNetPrice(new Money(5000, Currency.getInstance("USD")))
        .build();

    private final Bank mockBank = mock(Bank.class);
    private final Cart underTest = Cart.createEmptyCart(mockBank);

    @Test
    void testAddProductShouldNotStoreTheProductWhenProductIsNull() {
        // Given - When
        underTest.addProduct(null, 1);

        // Then
        assertTrue(underTest.getProductMap().isEmpty());
        verifyNoInteractions(mockBank);
    }

    @Test
    void testAddProductShouldNotStoreTheProductWhenAmountIsNegative() {
        // Given - When
        underTest.addProduct(HUF_PRODUCT, -1);

        // Then
        assertTrue(underTest.getProductMap().isEmpty());
        verifyNoInteractions(mockBank);
    }

    @Test
    void testAddProductShouldStoreTheProductWhenItIsNotNull() {
        // Given - When
        underTest.addProduct(HUF_PRODUCT, 1);

        // Then
        assertEquals(1, underTest.getProductMap().size());
        verifyNoInteractions(mockBank);
    }

    @Test
    void testGetAggregatedNetPriceShouldReturnZeroPriceWhenNoItemsAreInTheCart() {
        // Given - When
        Money result = underTest.getAggregatedNetPrice();

        // Then
        assertEquals(0, result.amount());
        verifyNoInteractions(mockBank);
    }

    @Test
    void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenCurrenciesAreTheSame() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 10);
        underTest.addProduct(HUF_PRODUCT, 5);

        // When
        Money result = underTest.getAggregatedNetPrice();

        // Then
        assertEquals(75000, result.amount());
        verifyNoInteractions(mockBank);
    }

    @Test
    void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenCurrenciesAreNotTheSame() {
        // Given
        underTest.addProduct(USD_PRODUCT, 2);
        when(mockBank.getExchangeRate(any())).thenReturn(Optional.of(10.0));

        // When
        Money result = underTest.getAggregatedNetPrice();

        // Then
        assertEquals(100000, result.amount());
        verify(mockBank).getExchangeRate(any());
    }

    @Test
    void testRemoveProductShouldRemoveItFromTheCartWhenItIsNotNull() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 10);

        // When
        underTest.removeProduct(HUF_PRODUCT);

        // Then
        assertFalse(underTest.containsProduct(HUF_PRODUCT));
    }

    @Test
    void testClearShouldRemoveEveryProductFromTheCart() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 10);

        // When
        underTest.clear();

        // Then
        assertTrue(underTest.isEmpty());
    }

    @Test
    void testContainsProductShouldReturnTrueWhenCartContainsTheGivenProduct() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 10);

        // When
        boolean actual = underTest.containsProduct(HUF_PRODUCT);

        // Then
        assertTrue(actual);
    }

    @Test
    void testContainsProductShouldReturnFalseWhenCartDoesNotContainTheGivenProduct() {
        // Given - When
        boolean actual = underTest.containsProduct(HUF_PRODUCT);

        // Then
        assertFalse(actual);
    }

    @Test
    void testIsEmptyShouldReturnFalseWhenCartIsNotEmpty() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 10);

        // When
        boolean actual = underTest.isEmpty();

        // Then
        assertFalse(actual);
    }

    @Test
    void testIsEmptyShouldReturnTrueWhenCartIsEmpty() {
        // Given - When
        boolean actual = underTest.isEmpty();

        // Then
        assertTrue(actual);
    }
}
