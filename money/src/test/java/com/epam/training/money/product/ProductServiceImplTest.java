package com.epam.training.money.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.training.money.impl.Money;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

  private final ProductService underTest = new ProductServiceImpl();

  @Test
  void testGetProductByNameShouldReturnHypoWhenInputProductNameIsHypo() {
    // Given
    Product expected = new Product("Xiaomi", new Money(100_000, Currency.getInstance("HUF")));
    // When
    Optional<Product> actual = underTest.getProductByName("Xiaomi");
    // Then
    assertTrue(actual.isPresent());
    assertEquals(expected, actual.get());
  }

  @Test
  void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
    // Given
    Optional<Product> expected = Optional.empty();
    // When
    Optional<Product> actual = underTest.getProductByName("Liszt");
    // Then
    assertTrue(actual.isEmpty());
    assertEquals(expected, actual);
  }

  @Test
  void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameIsNull() {
    // Given
    Optional<Product> expected = Optional.empty();
    // When
    Optional<Product> actual = underTest.getProductByName(null);
    // Then
    assertTrue(actual.isEmpty());
    assertEquals(expected, actual);
  }

}
