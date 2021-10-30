package com.epam.training.webshop.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import com.epam.training.webshop.domain.exception.NoSuchProductException;
import com.epam.training.webshop.domain.grossprice.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.domain.impl.ShoppingCartServiceImpl;
import com.epam.training.webshop.domain.order.model.Product;
import com.epam.training.webshop.domain.order.model.impl.SimpleCart;
import com.epam.training.webshop.domain.order.model.impl.SimpleProduct;
import com.epam.training.webshop.repository.OrderRepository;
import com.epam.training.webshop.repository.ProductRepository;
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
    private OrderRepository orderRepository;
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
        underTest = new ShoppingCartServiceImpl(new SimpleCart(), productRepository, orderRepository, grossPriceCalculatorDecorator);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() throws NoSuchProductException {
        // Given
        Product product = new SimpleProduct(PRODUCT_NAME, 100);
        List<Product> expectedResult = Collections.singletonList(product);
        given(productRepository.getAllProduct()).willReturn(Collections.singletonList(product));
        underTest = new ShoppingCartServiceImpl(new SimpleCart(), productRepository, orderRepository, grossPriceCalculatorDecorator);
        underTest.addProduct(PRODUCT_NAME);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        assertEquals(expectedResult, actualResult);
    }

}
