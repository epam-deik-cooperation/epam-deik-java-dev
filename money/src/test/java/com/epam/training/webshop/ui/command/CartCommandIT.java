package com.epam.training.webshop.ui.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.epam.training.webshop.core.checkout.CheckoutService;
import com.epam.training.webshop.core.checkout.persistence.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
class CartCommandIT {

    @Autowired
    private Shell shell;

    @Autowired
    private OrderRepository orderRepository;

    @SpyBean
    private CheckoutService checkoutService;

    @Test
    void testCartCheckoutCommandShouldCreateTheOrder() {
        // Given
        shell.evaluate(() -> "user login admin admin");
        shell.evaluate(() -> "user cart clear");
        shell.evaluate(() -> "user cart addProduct Hypo 100");
        shell.evaluate(() -> "user cart removeProduct Hypo");
        shell.evaluate(() -> "user cart addProduct Hypo 10");
        shell.evaluate(() -> "user cart addProduct Kecske 10");

        // When
        shell.evaluate(() -> "user cart checkout");

        // Then
        verify(checkoutService).checkout(any());
        assertEquals(1, orderRepository.findAll().size());
    }
}
