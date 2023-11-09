package com.epam.training.webshop.core.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.product.persistence.Product;
import com.epam.training.webshop.core.product.persistence.ProductRepository;
import java.util.Currency;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

    private static final Product ENTITY = new Product("Pentium3", 500.0, "HUF");
    private static final ProductDto DTO = new ProductDto.Builder()
        .withName("Pentium3")
        .withNetPrice(new Money(500.0, Currency.getInstance("HUF")))
        .build();

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductServiceImpl underTest = new ProductServiceImpl(productRepository);

    @Test
    void testGetProductByNameShouldReturnTheProductWhenItExists() {
        // Given
        when(productRepository.findByName("Pentium3")).thenReturn(Optional.of(ENTITY));
        Optional<ProductDto> expected = Optional.of(DTO);

        // When
        Optional<ProductDto> actual = underTest.getProductByName("Pentium3");

        // Then
        assertEquals(expected, actual);
        verify(productRepository).findByName("Pentium3");
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
        // Given
        when(productRepository.findByName("SSD")).thenReturn(Optional.empty());
        Optional<ProductDto> expected = Optional.empty();

        // When
        Optional<ProductDto> actual = underTest.getProductByName("SSD");

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(productRepository).findByName("SSD");
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameIsNull() {
        // Given
        when(productRepository.findByName(null)).thenReturn(Optional.empty());
        Optional<ProductDto> expected = Optional.empty();

        // When
        Optional<ProductDto> actual = underTest.getProductByName(null);

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(productRepository).findByName(null);
    }

    @Test
    void testCreateProductShouldStoreTheGivenProductWhenTheInputProductIsValid() {
        // Given
        when(productRepository.save(ENTITY)).thenReturn(ENTITY);

        // When
        underTest.createProduct(DTO);

        // Then
        verify(productRepository).save(ENTITY);
    }
}
