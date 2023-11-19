package com.epam.training.ticketservice.core.service.interfaces;

import com.epam.training.ticketservice.core.dto.MovieDto;
import com.epam.training.ticketservice.core.exceptions.AlreadyExists;
import com.epam.training.ticketservice.core.exceptions.DoesNotExists;

import java.util.List;

public interface MovieServiceInterface {
    void movieCreate(String movieName, String movieGenre, int watchTime) throws AlreadyExists;

    void movieUpdate(String movieName, String movieGenre, int watchTime) throws DoesNotExists;

    void movieDelete(String movieName) throws DoesNotExists;

    List<MovieDto> movieList();
}
