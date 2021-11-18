package com.epam.training.webshop.core.checkout.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grossprice.GrossPriceCalculator;
import com.epam.training.webshop.core.checkout.model.Order;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceImplTest {

    @Mock
    private GrossPriceCalculator grossPriceCalculator;

    @Mock
    private CheckoutObservable checkoutObservable;

    @InjectMocks
    private CheckoutServiceImpl underTest;

    @Test
    public void testCheckoutShouldReturnWithAnOrderWhenCartIsNotNull() {
        // Given
        List<ProductDto> productList = mock(List.class);
        Cart cart = mock(Cart.class);
        Money netPrice = mock(Money.class);
        Money grossPrice = mock(Money.class);
        when(cart.getProductList()).thenReturn(productList);
        when(cart.getAggregatedNetPrice()).thenReturn(netPrice);
        when(grossPriceCalculator.getAggregatedGrossPrice(cart)).thenReturn(grossPrice);
        Order expected = new Order(productList, netPrice, grossPrice);

        // When
        Order actual = underTest.checkout(cart);

        // Then
        assertEquals(expected, actual);
        verify(cart).getProductList();
        verify(cart).getAggregatedNetPrice();
        verify(grossPriceCalculator).getAggregatedGrossPrice(cart);
        verify(checkoutObservable).notifyObservers(expected);
        verifyNoMoreInteractions(grossPriceCalculator, cart, productList, netPrice, grossPrice, checkoutObservable);
    }

    @Test
    public void testCheckoutShouldThrowANullPointerExceptionWhenCartIsNull() {
        // Given
        // When
        assertThrows(NullPointerException.class, () -> underTest.checkout(null));

        // Then
        verifyNoMoreInteractions(grossPriceCalculator, checkoutObservable);
    }
}