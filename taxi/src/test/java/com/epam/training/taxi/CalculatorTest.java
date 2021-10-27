package com.epam.training.taxi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CalculatorTest {

    private Calculator underTest;

    @BeforeEach
    void setupClassInstance()
    {
        underTest = new Calculator();
    }

    @Test
    void testCalculatePriceShouldGiveCorrectPriceWhenGivenAnAccountWithDiscount()
    {
        //Given
        Double expected = 9900.0;
        Account account = new Account(1L,"Test",0.1);
        //When
        Double actual = underTest.calculatePrice(account,100.0);
        //Then
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testCalculatePriceShouldGiveCorrectPriceWhenGivenANullAccount()
    {
        //Given
        Double expected = 11000.0;
        //When
        Double actual = underTest.calculatePrice(null,100.0);
        //Then
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testGetOutputStringShouldGiveCorrectStringWhenGivenAValidAccount()
    {
        //Given
        String expected = "1,100.0,11000.0,0.0";
        Account account = new Account(1L,"Test",0.0);
        //When
        String actual = underTest.getOutputString(account,100.0,11000.0);
        //Then
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testGetOutputStringShouldGiveCorrectStringWhenGivenANonValidAccount()
    {
        //Given
        String expected = "null,100.0,11000.0,0";
        //When
        String actual = underTest.getOutputString(null,100.0,11000.0);
        //Then
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testGetCurrentAccountShouldGiveCorrectAccountWhenGivenMatchingID()
    {
        //Given
        Account expected = new Account(1L,"Pear",0.3);
        List<Account> list = List.of(
                new Account(2L,"Apple",0.4),
                new Account(1L,"Pear",0.3),
                new Account(3L,"Peach",0.0)
        );
        //When
        Account actual = underTest.getCurrentAccount(list,1L);
        //Then
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void testGetCurrentAccountShouldGiveNullAccountWhenGivenNonMatchingID()
    {
        //Given
        List<Account> list = List.of(
                new Account(2L,"Apple",0.4),
                new Account(1L,"Pear",0.3),
                new Account(3L,"Peach",0.0)
        );
        //When
        Account actual = underTest.getCurrentAccount(list,4L);
        //Then
        Assertions.assertNull(actual);
    }
}