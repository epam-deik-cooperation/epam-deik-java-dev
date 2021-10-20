package com.epam.training.webshop.core.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ProductServiceImplTest {

    private final ProductServiceImpl underTest = new ProductServiceImpl();

    @Test
    public void testGetProductListShouldReturnAStaticListWithTwoElement() {
        // Given
        // When
        List<Product> actual = underTest.getProductList();

        // Then
        assertEquals(2, actual.size());
    }

    @Test
    public void testGetProductByNameShouldReturnGPUWhenInputProductNameIsGPU() {
        // Given
        Product expected = new Product.Builder()
            .withName("GPU")
            .withNetPrice(new Money(600_000, Currency.getInstance("HUF")))
            .build();

        // When
        Optional<Product> actual = underTest.getProductByName("GPU");

        // Then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testGetProductByNameShouldReturnPS5WhenInputProductNameIsPS5() {
        // Given
        Product expected = new Product.Builder()
            .withName("PS5")
            .withNetPrice(new Money(500_000, Currency.getInstance("HUF")))
            .build();

        // When
        Optional<Product> actual = underTest.getProductByName("PS5");

        // Then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
        // Given
        Optional<Product> expected = Optional.empty();

        // When
        Optional<Product> actual = underTest.getProductByName("Notebook");

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameIsNull() {
        // Given
        Optional<Product> expected = Optional.empty();

        // When
        Optional<Product> actual = underTest.getProductByName(null);

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
    }
}
