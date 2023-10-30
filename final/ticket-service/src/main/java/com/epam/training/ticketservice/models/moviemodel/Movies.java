package com.epam.training.ticketservice.models.moviemodel;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Movies {
    private List<Movie> movies;

    public Movies() {
        movies = new ArrayList<>();
    }
    public void append(String movieName, String genre, int length){
        movies.add(new Movie(movieName, genre, length));
    }
}
