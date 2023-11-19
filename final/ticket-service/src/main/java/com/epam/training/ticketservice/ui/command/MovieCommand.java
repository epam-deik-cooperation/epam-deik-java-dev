package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.dto.MovieDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.MovieServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.StringJoiner;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {
    private final MovieServiceInterface movieServiceInterface;
    private final UserServiceInterface userServiceInterface;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Creates a movie")
    public String createMovie(String movieName, String genre, int length) throws AlreadyExists {
        movieServiceInterface.movieCreate(movieName, genre, length);
        return "The movie was created successfully";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Updates an existing movie")
    public String movieUpdater(String movieName, String genre, int length) throws DoesNotExists {
        movieServiceInterface.movieUpdate(movieName, genre, length);
        return "The movie was deleted successfully";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Deletes an existing movie")
    public String movieDeleter(String movieName) throws DoesNotExists {
        movieServiceInterface.movieDelete(movieName);
        return "The movie was updated successfully";
    }

    @ShellMethod(key = "list movies", value = "Lists all created movies")
    public String listMovies() {
        List<MovieDto> movieDtoList = movieServiceInterface.movieList();
        if (movieDtoList.isEmpty()) {
            return "There are no movies at the moment";
        }
        StringBuilder moviesReturned = new StringBuilder();
        StringJoiner joiner = new StringJoiner("\n");
        for (MovieDto movieDto : movieDtoList) {
            joiner.add(movieDto.toString());
        }
        return moviesReturned.append(joiner).toString();
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
