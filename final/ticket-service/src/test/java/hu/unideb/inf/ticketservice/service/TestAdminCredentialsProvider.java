package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAdminCredentialsProvider {

    private AdminCredentialsProvider underTest;

    @BeforeEach
    public void setup() {
        underTest = new AdminCredentialsProvider();
    }

    @Test
    public void testGetUsernameShouldGiveCorrectStringWhenCalled() {
        //Given
        final String expected = "admin";

        //When
        final String result = underTest.getUsername();

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testGetPasswordShouldGiveCorrectStringWhenCalled() {
        //Given
        final String expected = "admin";

        //When
        final String result = underTest.getPassword();

        //Then
        Assertions.assertEquals(expected,result);
    }
}
