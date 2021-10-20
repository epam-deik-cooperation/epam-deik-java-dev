package com.epam.training.ticketservice.screening;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    boolean existsByMovie_TitleAndRoom_NameAndDate(String movieTitle, String roomName, LocalDateTime date);

    void deleteByMovie_TitleAndRoom_NameAndDate(String movieTitle, String roomName, LocalDateTime date);

}
