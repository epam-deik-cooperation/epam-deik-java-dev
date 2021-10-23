package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.exception.DateConflictException;
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
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningService screeningService;

    private Room testRoom;
    private Movie testMovie;
    private Screening testScreening;
    private List<Screening> testList;


    @BeforeEach
    void setUp() {
        testRoom = Room.builder()
                .name("testRoom")
                .numberOfColumns(10)
                .numberOfRows(10)
                .build();

        testMovie = Movie.builder()
                .title("testMovie")
                .genre("test")
                .length(100)
                .build();

        testScreening = Screening.builder()
                .room(testRoom)
                .movie(testMovie)
                .date(LocalDateTime.of(2000, Month.APRIL, 30, 9, 50))
                .build();

        testList = List.of(Screening.builder()
                .movie(testMovie)
                .room(testRoom)
                .date(LocalDateTime.of(2000, Month.APRIL, 30, 10, 10))
                .build());

    }


    @Test
    public void testGetAllScreeningsShouldReturnListOfScreenings() {

        //Given
        List<Screening> expectedList = testList;

        //When
        when(screeningRepository.findAll()).thenReturn(expectedList);
        List<Screening> actualList = screeningService.getAllScreenings();

        //Then
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testCreateScreeningShouldSucceedIfThereIsNoConflictingDate() throws DateConflictException {

        // Given
        testScreening.setDate(LocalDateTime.of(1999, Month.APRIL, 10, 10, 10));
        testList.get(0).setDate(LocalDateTime.of(2000, Month.APRIL, 20, 20, 20));

        // When
        when(screeningRepository.findAll()).thenReturn(testList);
        screeningService.createScreening(testScreening);

        // Then
        verify(screeningRepository, times(1)).save(testScreening);
    }

    @Test
    public void testCreateScreeningShouldThrowExceptionIfThereIsConflictingDate() throws DateConflictException {

        // Given
        LocalDateTime time = LocalDateTime.of(1999, Month.APRIL, 10, 10, 10);
        testScreening.setDate(time);
        testList.get(0).setDate(time);

        // When
        when(screeningRepository.findAll()).thenReturn(testList);

        // Then
        assertThrows(DateConflictException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);
    }

    @Test
    public void testCreateScreeningShouldThrowExceptionIfNewScreeningWouldStartInBreakTime() throws DateConflictException {

        // Given
        LocalDateTime time = LocalDateTime.of(1999, Month.APRIL, 10, 10, 10);
        int movieLength = testMovie.getLength();
        testScreening.setDate(time.plusMinutes(movieLength+1));
        testList.get(0).setDate(time);

        // When
        when(screeningRepository.findAll()).thenReturn(testList);

        // Then
        assertThrows(DateConflictException.class, () -> screeningService.createScreening(testScreening));
        verify(screeningRepository, times(0)).save(testScreening);
    }

    @Test
    public void testCreateScreeningShouldSucceedWhenThereAreNoScreeningsInTheRoomYet() throws DateConflictException {

        // Given


        // When
        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());
        screeningService.createScreening(testScreening);

        // Then
        verify(screeningRepository, times(1)).save(testScreening);
    }

    @Test
    public void testDeleteScreening() throws NotFoundException {

        //Given


        //When
        when(screeningRepository.existsByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(anyString(), anyString(), any(LocalDateTime.class)))
                .thenReturn(true);
        screeningService.deleteScreening(testScreening.getMovie().getTitle(),
                testScreening.getRoom().getName(), testScreening.getDate());

        //Then
        verify(screeningRepository, times(1))
                .deleteByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(anyString(), anyString(), any(LocalDateTime.class));
    }


    @Test
    public void testDeleteScreeningShouldThrowNotFoundExceptionWhenExistByReturnsFalse() {

        //Given


        //When
        when(screeningRepository.existsByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(anyString(), anyString(), any(LocalDateTime.class)))
                .thenReturn(false);

        //Then
        assertThrows(NotFoundException.class,
                () -> screeningService.deleteScreening(testScreening.getMovie().getTitle(),
                        testScreening.getRoom().getName(), testScreening.getDate()));

        verify(screeningRepository, times(0))
                .deleteByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(anyString(), anyString(), any(LocalDateTime.class));
    }

}
