package com.epam.training.ticketservice.screening;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {


    boolean existsByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(String movieTitle,
                                                                                           String roomName,
                                                                                           LocalDateTime date);

    Screening findByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(String movieTitle,
                                                                                           String roomName,
                                                                                           LocalDateTime date);


    @Transactional
    void deleteByMovie_TitleContainingIgnoreCaseAndRoom_NameContainingIgnoreCaseAndDate(String movieTitle,
                                                                                        String roomName,
                                                                                        LocalDateTime date);

}
