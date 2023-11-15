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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ProductServiceImplTest {

    private static final Product ENTITY = new Product("Hypo", 550.0, "HUF");
    private static final ProductDto DTO = new ProductDto.Builder()
        .withName("Hypo")
        .withNetPrice(new Money(550.0, Currency.getInstance("HUF")))
        .build();

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductServiceImpl underTest = new ProductServiceImpl(productRepository);

    @Test
    void testGetProductByNameShouldReturnHypoWhenInputProductNameIsHypo() {
        // Given
        when(productRepository.findByName("Hypo")).thenReturn(Optional.of(ENTITY));
        Optional<ProductDto> expected = Optional.of(DTO);

        // When
        Optional<ProductDto> actual = underTest.getProductByName("Hypo");

        // Then
        assertEquals(expected, actual);
        verify(productRepository).findByName("Hypo");
    }

    @Test
    void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
        // Given
        when(productRepository.findByName("dummy")).thenReturn(Optional.empty());
        Optional<ProductDto> expected = Optional.empty();

        // When
        Optional<ProductDto> actual = underTest.getProductByName("dummy");

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(productRepository).findByName("dummy");
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

    @Test
    void testGetProductListShouldReturnTheAvailableProducts() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(ENTITY));

        // When
        List<ProductDto> actual = underTest.getProductList();

        // Then
        verify(productRepository).findAll();
        assertEquals(1, actual.size());
    }
}
