package com.epam.training.webshop.ui.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
class ProductCommandIT {

    private static final ProductDto PRODUCT_DTO = ProductDto.builder()
        .withName("Tej")
        .withNetPrice(new Money(100, Currency.getInstance("HUF")))
        .build();

    @Autowired
    private Shell shell;

    @SpyBean
    private ProductService productService;

    @Test
    void testProductCreateCommandShouldNotSaveTheProductWhenUserIsLoggedIn() {
        // Given
        shell.evaluate(() -> "user register user1 user1");
        shell.evaluate(() -> "user login user1 user1");

        // When
        shell.evaluate(() -> "admin product create Tej 100");

        // Then
        verify(productService, times(0)).createProduct(PRODUCT_DTO);
    }

    @Test
    void testProductCreateCommandShouldNotSaveTheProductWhenNobodyIsLoggedIn() {
        // Given
        // When
        shell.evaluate(() -> "admin product create Tej 100");

        // Then
        verify(productService, times(0)).createProduct(PRODUCT_DTO);
    }

    @Test
    void testProductCreateCommandShouldSaveTheProductWhenAdminIsLoggedIn() {
        // Given
        shell.evaluate(() -> "user login admin admin");

        // When
        shell.evaluate(() -> "admin product create Tej 100");

        // Then
        verify(productService).createProduct(PRODUCT_DTO);
        assertTrue(productService.getProductList().contains(PRODUCT_DTO));
    }
}
