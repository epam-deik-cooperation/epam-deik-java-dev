package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.ConflictException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.room.Room;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreeningCommandsTest {

    @Mock
    ScreeningService screeningService;

    @Mock
    ScreeningRepository screeningRepository;

    @InjectMocks
    ScreeningCommands screeningCommands;

    private String movieTitle;
    private String roomName;
    private String date;
    private Screening screening;

    @BeforeEach
    void setUp() {
        movieTitle = "test";
        roomName = "test";
        date = "2021-11-11 11:11";
        screening = Screening.builder()
                .movie(Movie.builder()
                        .title(movieTitle).build())
                .room(Room.builder()
                        .name(roomName).build())
                .date(LocalDateTime.of(2021, 11, 11, 11, 11)).build();
    }

    @Test
    public void testCreateScreening() throws ConflictException, NotFoundException {

        // Given


        // When
        when(screeningService.mapToScreening(movieTitle, roomName, date)).thenReturn(screening);

        screeningCommands.createScreening(movieTitle, roomName, date);

        // Then
        verify(screeningService, times(1)).createScreening(any(Screening.class));
    }


    @Test
    public void testCreateScreeningShouldNotSaveScreeningIfConflictExceptionIsCaught()
            throws ConflictException, NotFoundException {

        // Given


        // When
        when(screeningService.mapToScreening(movieTitle, roomName, date)).thenReturn(screening);
        doThrow(ConflictException.class).when(screeningService).createScreening(any(Screening.class));

        screeningCommands.createScreening(movieTitle, roomName, date);

        // Then
        verify(screeningRepository, times(0)).save(any(Screening.class));
    }

    @Test
    public void testCreateScreeningShouldNotSaveScreeningIfNotFoundExceptionIsCaught()
            throws NotFoundException {

        // Given


        // When

        doThrow(NotFoundException.class).when(screeningService).mapToScreening(movieTitle, roomName, date);

        screeningCommands.createScreening(movieTitle, roomName, date);

        // Then
        verify(screeningRepository, times(0)).save(any(Screening.class));
    }


    @Test
    public void testDeleteScreening() throws NotFoundException {

        // Given


        // When
        when(screeningService.mapToScreening(movieTitle, roomName, date)).thenReturn(screening);
        screeningCommands.deleteScreening(movieTitle, roomName, date);

        // Then
        verify(screeningService, times(1))
                .deleteScreening(any(Screening.class));
    }

    @Test
    public void testDeleteScreeningShouldNotDeleteScreeningIfNotFoundExceptionIsCaught()
            throws NotFoundException {

        // Given


        // When
        doThrow(NotFoundException.class)
                .when(screeningService)
                .mapToScreening(movieTitle, roomName, date);

        screeningCommands.deleteScreening(movieTitle, roomName, date);

        // Then
        verify(screeningRepository, times(0))
                .deleteByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class));
    }

    @Test
    public void testListScreeningsShouldReturnExpectedStringIfNoScreeningsAreFound() {

        // Given
        String expectedString = "There are no screenings";

        // When
        when(screeningService.getAllScreenings()).thenReturn(Collections.emptyList());
        String actualString = screeningCommands.listScreenings();

        // Then
        assertEquals(expectedString, actualString);
    }

    @Test
    public void testListScreeningsShouldReturnStringWithFoundScreenings() {

        // Given
        Screening testScreening1 = Screening.builder()
                .room(new Room())
                .movie(new Movie())
                .date(LocalDateTime.now())
                .build();


        Screening testScreening2 = Screening.builder()
                .room(new Room())
                .movie(new Movie())
                .date(LocalDateTime.now())
                .build();

        String expectedString = testScreening1 + "\n" + testScreening2;

        // When
        when(screeningService.getAllScreenings()).thenReturn(List.of(testScreening1, testScreening2));
        String actualString = screeningCommands.listScreenings();

        // Then
        assertEquals(expectedString, actualString);
    }


}
