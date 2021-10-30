package com.epam.training.taxi;

import com.epam.training.taxi.account.Account;
import com.epam.training.taxi.account.RegisteredAccount;
import com.epam.training.taxi.account.UnregisteredAccount;
import com.epam.training.taxi.calculator.SimpleCalculator;
import com.epam.training.taxi.exception.NegativeDistanceException;
import com.epam.training.taxi.invoice.Invoice;
import com.epam.training.taxi.invoice.SimpleInvoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {

    private final SimpleCalculator underTest = new SimpleCalculator(100);

    @Test
    public void testCalculateShouldThrowExceptionWhenGivenNegativeDistance() {
        // Given
        Account account = new UnregisteredAccount();
        Double distance = -1.0;

        // When, then
        assertThrows(NegativeDistanceException.class, () -> underTest.calculate(account, distance));
    }

    @Test
    public void testCalculateShouldReturnCorrectInvoiceWhenGivenPositiveDistance() {
        // Given
        Account account = new RegisteredAccount(1L, "Test", 0.25);
        Double distance = 1.0;
        Invoice expected = new SimpleInvoice(1L, distance, 75.0, 25.0);

        // When
        Invoice actual = underTest.calculate(account, distance);

        // Then
        assertEquals(expected, actual);

    }
}
