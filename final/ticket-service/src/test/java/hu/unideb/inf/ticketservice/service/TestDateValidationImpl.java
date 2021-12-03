package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.service.impl.DateValidationImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestDateValidationImpl {

    private DateValidationImpl underTest;

    @BeforeEach
    public void setup() {
        underTest = new DateValidationImpl();
    }

    @Test
    public void testIsDateValidReturnTrueIfTheGivenDateFormatIsValid() {
        //Given
        final String givenDate = "2021-10-10 10:00";
        //When
        final boolean result = underTest.isDateValid(givenDate);

        //Then
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsDateValidReturnFalseIfTheGivenDateFormatIsInValid() {
        //Given
        final String givenDate = "2021.10.10 10:00";
        //When
        final boolean result = underTest.isDateValid(givenDate);

        //Then
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsDateOverlappingShouldReturnTrueIfTheGivenDateIsInsideAnotherDateInterval() {
        //Given
        final String givenDate = "2021-10-10 10:00";
        final Movie movie = new Movie("Movie","genre",100);
        final Room room = new Room("Room",10,10);
        final Screening screening = new Screening(movie,room,"2021-10-10 09:00");
        final List<Screening> screeningList = List.of(screening);
        //When
        final boolean result = underTest.isDateOverlapping(givenDate,screeningList,room,100);

        //Then
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsDateOverlappingShouldReturnFalseIfTheGivenDateIsNotInsideAnotherDateInterval() {
        //Given
        final String givenDate = "2021-10-10 10:00";
        final Movie movie = new Movie("Movie","genre",50);
        final Room room = new Room("Room",10,10);
        final Screening screening = new Screening(movie,room,"2021-10-10 09:00");
        final List<Screening> screeningList = List.of(screening);
        //When
        final boolean result = underTest.isDateOverlapping(givenDate,screeningList,room,50);

        //Then
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsDateInsideBreakTimeShouldReturnTrueIfTheGivenDateIsInsideAnotherDateIntervalPlus10Minutes() {
        //Given
        final String givenDate = "2021-10-10 10:00";
        final Movie movie = new Movie("Movie","genre",55);
        final Room room = new Room("Room",10,10);
        final Screening screening = new Screening(movie,room,"2021-10-10 09:00");
        final List<Screening> screeningList = List.of(screening);
        //When
        final boolean result = underTest.isDateInsideBreakTime(givenDate,screeningList,room,55);

        //Then
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsDateInsideBreakTimeShouldReturnFalseIfTheGivenDateIsNotInsideAnotherDateIntervalPlus10Minutes() {
        //Given
        final String givenDate = "2021-10-10 10:00";
        final Movie movie = new Movie("Movie","genre",45);
        final Room room = new Room("Room",10,10);
        final Screening screening = new Screening(movie,room,"2021-10-10 09:00");
        final List<Screening> screeningList = List.of(screening);
        //When
        final boolean result = underTest.isDateInsideBreakTime(givenDate,screeningList,room,45);

        //Then
        Assertions.assertFalse(result);
    }
}
