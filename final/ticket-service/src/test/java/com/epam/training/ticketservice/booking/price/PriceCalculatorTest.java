package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.screening.Screening;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceCalculatorTest {

    @Mock
    PriceComponentService pcService;

    @InjectMocks
    PriceCalculator priceCalculator;


    private static Stream<Arguments> provideArgumentsForTestCalculate() {

        return Stream.of(Arguments.of(100, 1, (PriceCalculator.getBasePrice() + 100) * 1),
                Arguments.of(100, 2, (PriceCalculator.getBasePrice() + 100) * 2),
                Arguments.of(100, 4, (PriceCalculator.getBasePrice() + 100) * 4),
                Arguments.of(100, 10, (PriceCalculator.getBasePrice() + 100) * 10),
                Arguments.of(100, 3, (PriceCalculator.getBasePrice() + 100) * 3),
                Arguments.of(100, 25, (PriceCalculator.getBasePrice() + 100) * 25));
    }


    @ParameterizedTest
    @MethodSource("provideArgumentsForTestCalculate")
    public void testCalculate(int additionalPrice, int quantityOfTickets, int expectedPrice) {

        // Given
        Screening testScreening = Screening.builder()
                .movie(new Movie())
                .room(new Room())
                .date(LocalDateTime.now())
                .build();


        // When
        when(pcService.getAdditionalPrice(testScreening)).thenReturn(additionalPrice);

        int actualPrice = priceCalculator.calculate(testScreening, quantityOfTickets);


        // Then
        assertEquals(expectedPrice, actualPrice);


    }


}
