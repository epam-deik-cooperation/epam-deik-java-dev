package com.epam.training.webshop.core.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

    private final ProductService underTest = new ProductServiceImpl();

    @Test
    void testGetProductByNameShouldReturnTheProductWhenItExists() {
        // Given
        Product expected = new Product("Pentium3", new Money(500, Currency.getInstance("HUF")));

        // When
        Optional<Product> actual = underTest.getProductByName("Pentium3");

        // Then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
        // Given
        Optional<Product> expected = Optional.empty();

        // When
        Optional<Product> actual = underTest.getProductByName("SSD");

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
