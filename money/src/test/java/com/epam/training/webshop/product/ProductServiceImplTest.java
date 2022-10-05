package com.epam.training.webshop.product;

import com.epam.training.webshop.finance.money.Money;
import com.epam.training.webshop.product.model.Product;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

    private final ProductService underTest = new ProductServiceImpl();

    @Test
    void testGetProductListShouldReturnAStaticListWithTwoElements() {
        // Given - When
        List<Product> actual = underTest.getProductList();

        // Then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void testGetProductByNameShouldReturnHypoWhenInputProductNameIsHypo() {
        // Given
        Product expected = new Product.Builder()
            .withName("Hypo")
            .withNetPrice(new Money(550, Currency.getInstance("HUF")))
            .build();

        // When
        Optional<Product> actual = underTest.getProductByName("Hypo");

        // Then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected, actual.get());
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
        // Given
        Optional<Product> expected = Optional.empty();

        // When
        Optional<Product> actual = underTest.getProductByName("Liszt");

        // Then
        Assertions.assertTrue(actual.isEmpty());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameIsNull() {
        // Given
        Optional<Product> expected = Optional.empty();

        // When
        Optional<Product> actual = underTest.getProductByName(null);

        // Then
        Assertions.assertTrue(actual.isEmpty());
        Assertions.assertEquals(expected, actual);
    }
}
