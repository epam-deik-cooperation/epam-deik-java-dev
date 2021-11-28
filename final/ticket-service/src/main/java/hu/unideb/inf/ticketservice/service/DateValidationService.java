package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;

import java.util.List;

public interface DateValidationService {

    boolean isDateValid(String dateString);

    boolean isDateOverlapping(String givenDate, List<Screening> screenings, Room room, Integer movieLength);

    boolean isDateInsideBreakTime(String givenDate, List<Screening> screenings, Room room, Integer movieLength);

}
