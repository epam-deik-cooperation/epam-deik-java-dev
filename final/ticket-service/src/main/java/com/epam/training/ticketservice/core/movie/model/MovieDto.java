package com.epam.training.ticketservice.core.movie.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Builder
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MovieDto {

    private final String name;
    private String theme;
    private int length;

    @Override
    public String toString() {
        return name + "(" + theme + ", " + length + " minutes)";
    }
}
