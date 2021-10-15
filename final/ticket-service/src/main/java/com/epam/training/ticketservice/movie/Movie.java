package com.epam.training.ticketservice.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {


    @Id
    @Column(name = "id")
    private String title;

    @Column(name = "length")
    private Integer length;

    @Column(name = "genre")
    private String genre;



}
