package com.epam.training.money.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import com.epam.training.money.bank.Bank;
import com.epam.training.money.impl.Money;
import com.epam.training.money.product.Product;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class CartTest {

  private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
  private final Bank mockBank = mock(Bank.class);
  private final Cart underTest = Cart.createEmptyCart(mockBank);

  @Test
  void testAddProductShouldNotStoreTheProductWhenItIsNull() {
    // Given - When
    underTest.add(null, 1);
    // Then
    assertTrue(underTest.getProducts().isEmpty());
    verifyNoInteractions(mockBank);
  }

  @Test
  void testAddProductShouldStoreTheProductWhenItIsNotNull() {
    // Given
    Product product = new Product("productName", new Money(10, HUF_CURRENCY));
    // When
    underTest.add(product, 1);
    // Then
    assertFalse(underTest.getProducts().isEmpty());
    assertTrue(underTest.getProducts().containsKey(product));
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
  void testGetAggregatedNetPriceShouldReturnCorrectPriceWhenOneItemInTheCart() {
    // Given
    Product product = new Product("productName", new Money(10, HUF_CURRENCY));
    underTest.add(product, 10);
    // When
    Money result = underTest.getAggregatedNetPrice();
    // Then
    assertEquals(100, result.value());
    assertEquals(HUF_CURRENCY, result.currency());
    verifyNoInteractions(mockBank);
  }
}
