package com.epam.training.webshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.domain.order.Cart;
import com.epam.training.webshop.domain.order.impl.CartImpl;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.order.model.impl.SimpleProduct;
import com.epam.training.webshop.repository.OrderRepository;

class ShoppingCartTest {

    private Cart underTest;

    @Mock
    private GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CartImpl(orderRepository, grossPriceCalculatorDecorator);
    }

    @Test
    public void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given
        List<Product> expectedResult = Collections.emptyList();

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
        // Given
        Product product = new SimpleProduct("Zöld alma", 100);
        List<Product> expectedResult = Collections.singletonList(product);

        underTest.addProduct(product);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

}
