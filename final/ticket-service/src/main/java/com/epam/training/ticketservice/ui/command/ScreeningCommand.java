package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.dto.RoomDto;
import com.epam.training.ticketservice.core.dto.ScreeningDto;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.Screening;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.RoomServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.ScreeningServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {
    private final ScreeningServiceInterface screeningServiceInterface;
    private final UserServiceInterface userServiceInterface;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Creates a screening")
    public String createScreening(String movieName, String roomName, String screeningDate) {
        try {
            screeningServiceInterface.createScreening(movieName, roomName,
                    LocalDateTime.parse(screeningDate, formatter));
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Screening successfully created";
    }

    @Transactional
    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "deletes a screening")
    public String deleteScreening(String movieName, String roomName, String screeningDate) {
        try {
            screeningServiceInterface.deleteScreening(movieName, roomName,
                    LocalDateTime.parse(screeningDate, formatter));
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Screening successfully deleted";
    }

    @ShellMethod(key = "list screenings", value = "Lists all created screenings")
    public String listScreenings() {
        List<ScreeningDto> screeningDtoList = screeningServiceInterface.listScreenings();
        if (!screeningDtoList.isEmpty()) {
            StringBuilder screeningsReturned = new StringBuilder();
            StringJoiner joiner = new StringJoiner("\n");
            for (ScreeningDto screening : screeningDtoList) {
                joiner.add(screening.toString());
            }
            return  screeningsReturned.append(joiner).toString();
        } else {
            return "There are no screenings";
        }
    }

    public Availability isAvailable() {
        if (userServiceInterface.describeAccount().isPresent()
                && userServiceInterface.describeAccount().get().role().equals(User.Role.ADMIN)) {
            return Availability.available();
        } else {
            return Availability.unavailable("You are not authorized");
        }
    }
}
