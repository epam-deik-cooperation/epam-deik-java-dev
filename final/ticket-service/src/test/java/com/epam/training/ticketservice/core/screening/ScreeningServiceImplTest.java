package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.entity.Movie;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.entity.Room;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.entity.Screening;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScreeningServiceImplTest {

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final RoomService roomService = mock(RoomService.class);
    private final MovieService movieService = mock(MovieService.class);
    private final ScreeningService underTest = new ScreeningServiceImpl(screeningRepository,roomService,movieService);

    @Test
    void testGetMovieListShouldReturnEmpty(){
        //Given
        List<ScreeningDto> expected = List.of();

        //When
        List<ScreeningDto> actual = underTest.getScreenings();

        //Then
        assertEquals(expected,actual);
    }
    @Test
    void testGetMovieListShouldReturnValues(){
        //Given
        MovieDto movie = MovieDto.builder().name("TestMovie").build();
        RoomDto roomDto = RoomDto.builder().name("TestRoom").build();
        Screening screening = new Screening("TestMovie", "TestRoom", LocalDateTime.now(), LocalDateTime.now().plusMinutes(40));

        List<Screening> expected = List.of(screening);

        when(screeningRepository.findAll())
                .thenReturn(List.of(screening));
        when(movieService.getMovieByName("TestMovie")).thenReturn(Optional.of(movie));
        when(roomService.getRoomByName("TestRoom")).thenReturn(Optional.of(roomDto));

        //When
        List<ScreeningDto> actual = underTest.getScreenings();

        //Then
        assertEquals(expected.size(), actual.size());
        for(int i=0; i<expected.size();i++){
            assertEquals(expected.get(i).getMovie(),actual.get(i).getMovie().getName());
            assertEquals(expected.get(i).getRoom(),actual.get(i).getRoom().getName());
            assertEquals(expected.get(i).getStart(),actual.get(i).getStart());
        }
    }

    @Test
    void testGetScreeningShouldReturnEmpty(){
        //Given
        MovieDto movieDto = MovieDto.builder().name("TestMovie").build();
        RoomDto roomDto = RoomDto.builder().name("TestRoom").build();
        Optional<Screening> expected = Optional.empty();
        //When
        Optional<ScreeningDto> actual = underTest.getScreening(movieDto, roomDto,LocalDateTime.now());
        //Then
        assertEquals(expected,actual);
    }
    @Test
    void testGetScreeningShouldReturnValue(){
        MovieDto movieDto = MovieDto.builder().name("TestMovie").build();
        RoomDto roomDto = RoomDto.builder().name("TestRoom").build();
        LocalDateTime start = LocalDateTime.now();
        Screening screening = new Screening(movieDto.getName(), roomDto.getName(), start, start.plusMinutes(10));
        Optional<Screening> expected = Optional.of(screening);

        when(screeningRepository.findByMovieAndRoomAndStart(movieDto.getName(),roomDto.getName(), start))
                .thenReturn(Optional.of(screening));
        when(movieService.getMovieByName("TestMovie")).thenReturn(Optional.of(movieDto));
        when(roomService.getRoomByName("TestRoom")).thenReturn(Optional.of(roomDto));

        Optional<ScreeningDto> actual = underTest.getScreening(movieDto, roomDto, start);

        assertEquals(expected.get().getMovie(), actual.get().getMovie().getName());
        assertEquals(expected.get().getRoom(), actual.get().getRoom().getName());
        assertEquals(expected.get().getStart(), actual.get().getStart());
    }

    @Test
    void testRemoveScreeningShouldReturnFalse(){
        MovieDto movieDto = MovieDto.builder().name("TestMovie").build();
        RoomDto roomDto = RoomDto.builder().name("TestRoom").build();
        LocalDateTime start = LocalDateTime.now();
        ScreeningDto screeningDto = ScreeningDto.builder().movie(movieDto).room(roomDto).start(start).build();

        boolean expected = false;

        boolean actual = underTest.removeScreening(screeningDto);

        assertEquals(expected,actual);
    }

    @Test
    void testRemoveScreeningShouldReturnTrue(){
        MovieDto movieDto = MovieDto.builder().name("TestMovie").build();
        RoomDto roomDto = RoomDto.builder().name("TestRoom").build();
        LocalDateTime start = LocalDateTime.now();
        ScreeningDto screeningDto = ScreeningDto.builder().movie(movieDto).room(roomDto).start(start).build();
        Screening screening = new Screening(movieDto.getName(),roomDto.getName(),start,start.plusMinutes(10));
        boolean expected = true;

        when(screeningRepository.findByMovieAndRoomAndStart(movieDto.getName(),roomDto.getName(),start))
                .thenReturn(Optional.of(screening));

        boolean actual = underTest.removeScreening(screeningDto);

        assertEquals(expected,actual);
    }
}