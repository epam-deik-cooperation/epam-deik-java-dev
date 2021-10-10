package com.epam.training.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MoneyTest {

    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final double HUF_VALUE_TO_CONVERT = 1000;
    private static final double USD_VALUE = 10;

    private Money underTest;

    @Mock
    private MonetaryValueConversionService monetaryValueConversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new Money(USD_VALUE, USD_CURRENCY);
    }

    @Test
    public void testAddShouldReturnSummedValuesOfTheGivenMoneyAndActualMoneyWhenGivenHandledMoney() {
        // Given
        final Money moneyToAdd = new Money(HUF_VALUE_TO_CONVERT, HUF_CURRENCY);
        final Money expectedMoney = new Money(13.25, USD_CURRENCY);
        given(monetaryValueConversionService.convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY)).willReturn(3.25);
        // When
        final Money actual = underTest.add(moneyToAdd, monetaryValueConversionService);
        // Then
        assertEquals(expectedMoney, actual);
        verify(monetaryValueConversionService).convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY);
    }

    @Test
    public void testCompareToShouldReturnZeroWhenConvertedValueOfTheGivenMoneyHasSameValueLikeTheActualMoney() {
        // Given
        final Money convertedMoneyToCompare = new Money(HUF_VALUE_TO_CONVERT, HUF_CURRENCY);
        given(monetaryValueConversionService.convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY)).willReturn(USD_VALUE);
        // When
        final int actual = underTest.compareTo(convertedMoneyToCompare, monetaryValueConversionService);
        // Then
        assertEquals(0, actual);
        verify(monetaryValueConversionService).convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY);
    }

    @Test
    public void testCompareToShouldReturnNegativeOneWhenConvertedValueOfTheGivenMoneyGreaterThanValueOfTheActualMoney() {
        // Given
        final Money convertedMoneyToCompare = new Money(HUF_VALUE_TO_CONVERT, HUF_CURRENCY);
        given(monetaryValueConversionService.convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY)).willReturn(Double.MAX_VALUE);
        // When
        final int actual = underTest.compareTo(convertedMoneyToCompare, monetaryValueConversionService);
        // Then
        assertEquals(-1, actual);
        verify(monetaryValueConversionService).convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY);
    }

    @Test
    public void testCompareToShouldReturnPositiveOneWhenConvertedValueOfTheGivenMoneyLesserThanValueOfTheActualMoney() {
        // Given
        final Money convertedMoneyToCompare = new Money(HUF_VALUE_TO_CONVERT, HUF_CURRENCY);
        given(monetaryValueConversionService.convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY)).willReturn(Double.MIN_VALUE);
        // When
        final int actual = underTest.compareTo(convertedMoneyToCompare, monetaryValueConversionService);
        // Then
        assertEquals(1, actual);
        verify(monetaryValueConversionService).convert(HUF_VALUE_TO_CONVERT, HUF_CURRENCY, USD_CURRENCY);
    }
}
