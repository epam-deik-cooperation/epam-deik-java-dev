package com.epam.training.webshop.core.product.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.product.persistence.entity.Product;
import com.epam.training.webshop.core.product.persistence.repository.ProductRepository;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ProductServiceImplTest {

    private static final Product GPU_ENTITY = new Product("GPU", 600_000, "HUF");
    private static final Product PS5_ENTITY = new Product("PS5", 500_000, "HUF");
    private static final ProductDto GPU_DTO = new ProductDto.Builder()
        .withName("GPU")
        .withNetPrice(new Money(600_000, Currency.getInstance("HUF")))
        .build();
    private static final ProductDto PS5_DTO = new ProductDto.Builder()
        .withName("PS5")
        .withNetPrice(new Money(500_000, Currency.getInstance("HUF")))
        .build();

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductServiceImpl underTest = new ProductServiceImpl(productRepository);

    @Test
    public void testGetProductListShouldCallProductRepositoryAndReturnADtoList() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(GPU_ENTITY, PS5_ENTITY));
        List<ProductDto> expected = List.of(GPU_DTO, PS5_DTO);

        // When
        List<ProductDto> actual = underTest.getProductList();

        // Then
        assertEquals(expected, actual);
        verify(productRepository).findAll();
    }

    @Test
    public void testGetProductByNameShouldReturnAPS5DtoWhenTheProductExists() {
        // Given
        when(productRepository.findByName("PS5")).thenReturn(Optional.of(PS5_ENTITY));
        Optional<ProductDto> expected = Optional.of(PS5_DTO);

        // When
        Optional<ProductDto> actual = underTest.getProductByName("PS5");

        // Then
        assertEquals(expected, actual);
        verify(productRepository).findByName("PS5");
    }

    @Test
    public void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameDoesNotExist() {
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
    public void testGetProductByNameShouldReturnOptionalEmptyWhenInputProductNameIsNull() {
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
    public void testCreateProductShouldCallProductRepositoryWhenTheInputProductIsValid() {
        // Given
        when(productRepository.save(GPU_ENTITY)).thenReturn(GPU_ENTITY);

        // When
        underTest.createProduct(GPU_DTO);

        // Then
        verify(productRepository).save(GPU_ENTITY);
    }

    @Test
    public void testCreateProductShouldThrowNullPointerExceptionWhenProductIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createProduct(null));
    }

    @Test
    public void testCreateProductShouldThrowNullPointerExceptionWhenProductNameIsNull() {
        // Given
        ProductDto product = new ProductDto.Builder()
            .withName(null)
            .withNetPrice(new Money(230_000D, Currency.getInstance("HUF")))
            .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createProduct(product));
    }

    @Test
    public void testCreateProductShouldThrowNullPointerExceptionWhenNetPriceIsNull() {
        // Given
        ProductDto product = new ProductDto.Builder()
            .withName("Monitor")
            .withNetPrice(null)
            .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createProduct(product));
    }
}
