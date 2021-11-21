package com.epam.training.webshop.service.impl;

import com.epam.training.webshop.lib.ConfirmationService;
import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.service.OrderConfirmationService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class EmailConfirmationAdapterTest {

    private static final List<Product> EMPTY_PRODUCTS = Collections.emptyList();
    private static final List<Product> PRODUCTS = List.of(
            new Product("Szilva", 399.99),
            new Product("Dió", 3999.0)
    );

    private OrderConfirmationService underTest;

    @Mock
    private ConfirmationService emailConfirmationService;
    @Mock
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new EmailConfirmationAdapter(emailConfirmationService);
    }

    @Test
    public void testNotifyShouldDelegateSendConfirmationMessageAboutWithNoProductsWhenGivenEmptyCart() {
        // Given
        BDDMockito.given(cart.getProducts()).willReturn(EMPTY_PRODUCTS);

        // When
        underTest.notify(cart);

        // Then
        Mockito.verify(emailConfirmationService).sendConfirmationMessageAbout(EMPTY_PRODUCTS);
        Mockito.verifyNoMoreInteractions(emailConfirmationService);
    }

    @Test
    public void testNotifyShouldDelegateSendConfirmationMessageAboutWithProductsWhenGivenCartWithProducts() {
        // Given
        BDDMockito.given(cart.getProducts()).willReturn(PRODUCTS);

        // When
        underTest.notify(cart);

        // Then
        Mockito.verify(emailConfirmationService).sendConfirmationMessageAbout(PRODUCTS);
        Mockito.verifyNoMoreInteractions(emailConfirmationService);
    }

    @Test
    public void testSendOrderConfirmationShouldDelegateSendConfirmationMessageAboutWithNoProductsWhenGivenEmptyCart() {
        // Given
        BDDMockito.given(cart.getProducts()).willReturn(EMPTY_PRODUCTS);

        // When
        underTest.sendOrderConfirmation(cart);

        // Then
        Mockito.verify(emailConfirmationService).sendConfirmationMessageAbout(EMPTY_PRODUCTS);
        Mockito.verifyNoMoreInteractions(emailConfirmationService);
    }

    @Test
    public void testSendOrderConfirmationShouldDelegateSendConfirmationMessageAboutWithProductsWhenGivenNotEmptyCart() {
        // Given
        BDDMockito.given(cart.getProducts()).willReturn(PRODUCTS);

        // When
        underTest.sendOrderConfirmation(cart);

        // Then
        Mockito.verify(emailConfirmationService).sendConfirmationMessageAbout(PRODUCTS);
        Mockito.verifyNoMoreInteractions(emailConfirmationService);
    }
}