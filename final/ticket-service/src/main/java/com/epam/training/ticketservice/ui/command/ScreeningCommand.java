package com.epam.training.ticketservice.ui.command;


import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserDto;
import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommand {

    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final UserService userService;

    @ShellMethod(key = "create screening", value = "Creates a screening")
    @ShellMethodAvailability("isAvailable")
    public String createScreening(String movieName, String roomName, String startStr) {
        Optional<MovieDto> optionalMovie = movieService.getMovieByName(movieName);
        Optional<RoomDto> optionalRoom = roomService.getRoomByName(roomName);
        if (optionalMovie.isEmpty()) {
            return "Movie " + movieName + " doesn't exist";
        }
        if (optionalRoom.isEmpty()) {
            return "Room " + roomName + " doesn't exist";
        }
        LocalDateTime date;
        try {
            var pattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);
            date = LocalDateTime.parse(startStr, pattern);
        } catch (DateTimeParseException e) {
            return startStr + " is not in the proper format (YYYY-MM-dd hh:mm)";
        }

        MovieDto movie = optionalMovie.get();
        RoomDto room = optionalRoom.get();

        if (screeningService.isOverlappingWithAnother(room, date)) {
            return "There is an overlapping screening";
        }
        if (screeningService.isInBreakAfterScreening(room, date)) {
            return "This would start in the break period after another screening in this room";
        }
        var end = date.plusMinutes(movie.getLength());
        ScreeningDto screening = ScreeningDto.builder().movie(movie).room(room).start(date).end(end).build();

        screeningService.createScreening(screening);
        var movieStr = screening.getMovie().getName();
        var roomStr = screening.getRoom().getName();
        return "Screening with movie " + movieStr + " in " + roomStr + " created successfully";
    }

    @ShellMethod(key = "list screenings", value = "List all screenings")
    public String getScreenings() {
        List<ScreeningDto> screenings = screeningService.getScreenings();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        } else {
            StringBuilder result = new StringBuilder();
            screenings.forEach(screening -> {
                result.append(screening.toString() + "\n");
            });
            return result.toString().trim();
        }
    }

    @ShellMethod(key = "delete screening", value = "Deletes a screening if exists")
    @ShellMethodAvailability("isAvailable")
    public String deleteScreening(String movieName, String roomName, String startStr) {
        String errorMessage = "There are no screening with these credentials";
        Optional<MovieDto> optionalMovie = movieService.getMovieByName(movieName);
        Optional<RoomDto> optionalRoom = roomService.getRoomByName(roomName);
        if (optionalMovie.isEmpty() || optionalRoom.isEmpty()) {
            return errorMessage;
        }
        LocalDateTime start;
        try {
            var pattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);
            start = LocalDateTime.parse(startStr, pattern);
        } catch (DateTimeParseException e) {
            return errorMessage;
        }

        MovieDto movie = optionalMovie.get();
        RoomDto room = optionalRoom.get();


        Optional<ScreeningDto> screening = screeningService.getScreening(movie, room, start);
        if (screening.isEmpty()) {
            return errorMessage;
        } else {
            screeningService.removeScreening(screening.get());
            return "Screening " + screening.get() + " deleted successfully";
        }
    }


    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }
}
