package com.epam.training.webshop.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.service.exception.NoSuchProductException;
import com.epam.training.webshop.service.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.service.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.repository.CartRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.service.ShoppingCartService;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ShoppingCartTest {

    private static final String PRODUCT_NAME = "Zöld alma";

    private ShoppingCartService underTest;

    @Mock
    private GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given

        List<Product> expectedResult = Collections.emptyList();
        underTest = new ShoppingCartServiceImpl(new Cart(), productRepository, cartRepository, grossPriceCalculatorDecorator);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() throws NoSuchProductException {
        // Given
        Product product = new Product(PRODUCT_NAME, 100);
        List<Product> expectedResult = Collections.singletonList(product);
        given(productRepository.findAll()).willReturn(Collections.singletonList(product));
        underTest = new ShoppingCartServiceImpl(new Cart(), productRepository, cartRepository, grossPriceCalculatorDecorator);
        underTest.addProduct(PRODUCT_NAME);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

}
