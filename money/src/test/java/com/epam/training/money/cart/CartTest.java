package com.epam.training.money.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import com.epam.training.money.Money;
import com.epam.training.money.bank.Bank;
import com.epam.training.money.product.Product;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class CartTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Product HUF_PRODUCT = Product.builder()
        .withName("productName")
        .withNetPrice(new Money(10, HUF_CURRENCY))
        .build();

    private final Bank mockBank = mock(Bank.class);
    private final Cart underTest = Cart.createEmptyCart(mockBank);

    @Test
    void testAddProductShouldNotStoreTheProductWhenItIsNull() {
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
        assertFalse(underTest.getProductMap().isEmpty());
        assertTrue(underTest.getProductMap().containsKey(HUF_PRODUCT));
        verifyNoInteractions(mockBank);
    }

    @Test
    void testGetAggregatedNetPriceShouldReturnZeroPriceWhenNoItemsAreInTheCart() {
        // Given
        Money expected = new Money(0, HUF_CURRENCY);

        // When
        Money result = underTest.getAggregatedNetPrice();

        // Then
        assertEquals(expected, result);
        verifyNoInteractions(mockBank);
    }

    @Test
    void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenThereAreItemsInTheCart() {
        // Given
        underTest.addProduct(HUF_PRODUCT, 5);
        underTest.addProduct(HUF_PRODUCT, 5);

        // When
        Money result = underTest.getAggregatedNetPrice();

        // Then
        assertEquals(100, result.amount());
        verifyNoInteractions(mockBank);
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
