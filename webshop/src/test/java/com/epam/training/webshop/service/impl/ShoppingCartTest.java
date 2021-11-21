package com.epam.training.webshop.service.impl;

import static org.mockito.BDDMockito.given;
import com.epam.training.webshop.model.Cart;
import com.epam.training.webshop.model.Coupon;
import com.epam.training.webshop.model.Order;
import com.epam.training.webshop.model.Product;
import com.epam.training.webshop.repository.CartRepository;
import com.epam.training.webshop.repository.ProductRepository;
import com.epam.training.webshop.service.ShoppingCartService;
import com.epam.training.webshop.service.exception.NoSuchProductException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ShoppingCartTest {

    private static final double FULL_NET_PRICE = 600;
    private static final double FULL_GROSS_PRICE = 762.0;
    private static final double GROSS_VALUE = 162.0;
    private static final String APPLE_PRODUCT_NAME = "Alma";
    private static final String PEAR_PRODUCT_NAME = "Körte";
    private static final Product APPLE_PRODUCT = new Product(APPLE_PRODUCT_NAME, 200.0);
    private static final Product PEAR_PRODUCT = new Product(PEAR_PRODUCT_NAME, 400.0);
    private static final List<Product> PRODUCTS = List.of(APPLE_PRODUCT, PEAR_PRODUCT);
    private static final List<Coupon> COUPONS = List.of(
            new Coupon("Novemberi alap kedvezmény", 50.0),
            new Coupon("Vásárló csalogató akció", 99.9)
    );

    private ShoppingCartService underTest;

    @Mock
    private GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Cart cart;
    @Mock
    private DummyWarehouseService dummyWarehouseService;
    @Mock
    private DummyOrderConfirmationService dummyOrderConfirmationService;

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
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testListProductShouldReturnTheListOfProductsWhenNotEmpty() throws NoSuchProductException {
        // Given
        List<Product> expectedResult = Collections.singletonList(APPLE_PRODUCT);
        BDDMockito.given(productRepository.findAll()).willReturn(Collections.singletonList(APPLE_PRODUCT));
        underTest = new ShoppingCartServiceImpl(new Cart(), productRepository, cartRepository, grossPriceCalculatorDecorator);
        underTest.addProduct(APPLE_PRODUCT_NAME);

        // When
        List<Product> actualResult = underTest.getProductsFromCart();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testGetTotalGrossPriceShouldReturnGrossPriceWhenGivenCartWithProducts() {
        // Given
        underTest = new ShoppingCartServiceImpl(cart, productRepository, cartRepository, grossPriceCalculatorDecorator);
        BDDMockito.given(grossPriceCalculatorDecorator.getAggregatedGrossPrice(underTest)).willReturn(GROSS_VALUE);

        // When
        final double actual = underTest.getTotalGrossPrice();

        // Then
        Assertions.assertEquals(GROSS_VALUE, actual);
    }

    @Test
    public void testOrderShouldReturnOrderDetailsWhenOrderProducts() {
        // Given
        final Order expected = new Order(PRODUCTS, FULL_NET_PRICE, FULL_GROSS_PRICE);
        underTest = new ShoppingCartServiceImpl(cart, productRepository, cartRepository, grossPriceCalculatorDecorator);
        given(cart.getProducts()).willReturn(PRODUCTS);
        given(grossPriceCalculatorDecorator.getAggregatedGrossPrice(underTest)).willReturn(FULL_GROSS_PRICE);
        underTest.subscribe(dummyWarehouseService);
        underTest.subscribe(dummyOrderConfirmationService);

        // When
        final Order actual = underTest.order();

        // Then
        Mockito.verify(cartRepository).save(cart);
        Mockito.verifyNoMoreInteractions(cartRepository);
        Mockito.verify(dummyOrderConfirmationService).notify(cart);
        Mockito.verifyNoMoreInteractions(dummyOrderConfirmationService);
        Mockito.verify(dummyWarehouseService).notify(cart);
        Mockito.verifyNoMoreInteractions(dummyWarehouseService);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testGetBasePriceShouldReturnAggregatedNetPriceOfProductsWhenGivenCartWithProducts() {
        // Given
        underTest = new ShoppingCartServiceImpl(cart, productRepository, cartRepository, grossPriceCalculatorDecorator);
        given(cart.getProducts()).willReturn(PRODUCTS);

        // When
        final double actual = underTest.getBasePrice();

        // Then
        Assertions.assertEquals(FULL_NET_PRICE, actual);
    }

    @Test
    public void testGetDiscountForCouponsShouldReturnAggregatedValueOfCouponsWhenThereWereActiveCoupons() {
        // Given
        final double expected = 149.9;
        underTest = new ShoppingCartServiceImpl(cart, productRepository, cartRepository, grossPriceCalculatorDecorator);
        given(cart.getCoupons()).willReturn(COUPONS);

        // When
        final double actual = underTest.getDiscountForCoupons();

        // Then
        Assertions.assertEquals(expected, actual);
    }

}
