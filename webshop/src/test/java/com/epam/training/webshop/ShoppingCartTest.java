package com.epam.training.webshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    private ShoppingCart underTest;

    @BeforeEach
    public void setUp() {
        underTest = new ShoppingCart();
    }

    @Test
    public void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given
        List<Product> expectedResult = Collections.emptyList();

        // When
        List<Product> actualResult = underTest.listProduct();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
        // Given
        Product product = new Product();
        List<Product> expectedResult = Collections.singletonList(product);

        underTest.addProduct(product);

        // When
        List<Product> actualResult = underTest.listProduct();

        // Then
        assertEquals(expectedResult, actualResult);
    }

}
