package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceCommandsTest {

    private Account account;


    @Mock
    PriceComponentService pcService;

    @Mock
    PriceComponentRepository pcRepository;

    @InjectMocks
    PriceCommands priceCommands;


    @Test
    public void testUpdateBasePrice() {

        // Given


        // When
        priceCommands.updateBasePrice(1000);

        // Then
        assertEquals(1000, PriceCalculator.getBasePrice());
    }

    @Test
    public void testCreatePriceComponent() throws AlreadyExistsException {

        // Given
        String componentName = "test";
        int testPrice = 1000;

        // When
        priceCommands.createPriceComponent(componentName, testPrice);

        // Then
        verify(pcService, times(1)).createPriceComponent(componentName, testPrice);
    }

    @Test
    public void testCreatePriceComponentShouldReturnExceptionMessageIfAlreadyExistsExceptionIsThrown()
            throws AlreadyExistsException {

        // Given
        int testPrice = 1000;
        String componentName = "test";
        String errorMessage = "test";
        AlreadyExistsException e = new AlreadyExistsException(errorMessage);

        // When
        doThrow(e).when(pcService).createPriceComponent(componentName, testPrice);
        String message = priceCommands.createPriceComponent(componentName, testPrice);


        // Then
        assertEquals(errorMessage, message);
        verify(pcRepository, times(0)).save(any(PriceComponent.class));
    }


    @Test
    public void testAttachComponentToMovie() throws NotFoundException {

        // Given
        String componentName = "test";
        String movieTitle = "test";

        // When
        priceCommands.attachComponentToMovie(componentName, movieTitle);

        // Then
        verify(pcService, times(1)).attachPriceComponentToMovie(componentName, movieTitle);

    }

    @Test
    public void testAttachComponentToMovieShouldReturnExceptionMessageIfNotFoundExceptionIsThrown()
            throws NotFoundException {

        // Given
        String componentName = "test";
        String movieTitle = "test";
        String errorMessage = "test";
        NotFoundException e = new NotFoundException(errorMessage);

        // When
        doThrow(e).when(pcService).attachPriceComponentToMovie(componentName, movieTitle);
        String message = priceCommands.attachComponentToMovie(componentName, movieTitle);


        // Then
        assertEquals(errorMessage, message);
        verify(pcRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    public void testAttachComponentToRoom() throws NotFoundException {

        // Given
        String componentName = "test";
        String roomName = "test";

        // When
        priceCommands.attachComponentToRoom(componentName, roomName);

        // Then
        verify(pcService, times(1)).attachPriceComponentToRoom(componentName, roomName);

    }

    @Test
    public void testAttachComponentToRoomShouldReturnExceptionMessageIfNotFoundExceptionIsThrown()
            throws NotFoundException {

        // Given
        String componentName = "test";
        String roomName = "test";
        String errorMessage = "test";
        NotFoundException e = new NotFoundException(errorMessage);

        // When
        doThrow(e).when(pcService).attachPriceComponentToRoom(componentName, roomName);
        String message = priceCommands.attachComponentToRoom(componentName, roomName);


        // Then
        assertEquals(errorMessage, message);
        verify(pcRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    public void testAttachComponentToScreening() throws NotFoundException {

        // Given
        String componentName = "test";
        String roomName = "test";
        String movieTitle = "test";
        String date = "2021-11-11 11:11";

        // When
        priceCommands.attachComponentToScreening(componentName, movieTitle, roomName, date);

        // Then
        verify(pcService, times(1))
                .attachPriceComponentToScreening(componentName, movieTitle, roomName, date);

    }

    @Test
    public void testAttachComponentToScreeningShouldReturnExceptionMessageIfNotFoundExceptionIsThrown()
            throws NotFoundException {

        // Given
        String componentName = "test";
        String roomName = "test";
        String movieTitle = "test";
        String date = "2021-11-11 11:11";
        String errorMessage = "test";
        NotFoundException e = new NotFoundException(errorMessage);

        // When
        doThrow(e).when(pcService).attachPriceComponentToScreening(componentName, movieTitle, roomName, date);
        String message = priceCommands.attachComponentToScreening(componentName, movieTitle, roomName, date);


        // Then
        assertEquals(errorMessage, message);
        verify(pcRepository, times(0)).save(any(PriceComponent.class));
    }


}




