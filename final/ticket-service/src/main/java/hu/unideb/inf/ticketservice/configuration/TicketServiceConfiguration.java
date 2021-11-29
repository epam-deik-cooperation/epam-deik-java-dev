package hu.unideb.inf.ticketservice.configuration;

import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.repository.*;
import hu.unideb.inf.ticketservice.service.DateValidationService;
import hu.unideb.inf.ticketservice.service.connection.*;
import hu.unideb.inf.ticketservice.service.connection.impl.*;
import hu.unideb.inf.ticketservice.service.impl.DateValidationImpl;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketServiceConfiguration {
    @Bean
    public LoggedInUserTrackImpl loggedInUserTrackImpl(UserInterface defaultUser) {
        return new LoggedInUserTrackImpl(defaultUser);
    }

    @Bean
    public UserInterface defaultUser() {
        return new DefaultUser();
    }

    @Bean
    DateValidationService dateValidationService() {
        return new DateValidationImpl();
    }

    @Bean
    ConnectToUserRepository userRepositoryConnection(UserRepository userRepository) {
        return new UserRepositoryConnection(userRepository);
    }

    @Bean
    ConnectToBookedSeatRepository bookedSeatRepositoryConnection(BookedSeatRepository bookedSeatRepository) {
        return new BookedSeatRepositoryConnection(bookedSeatRepository);
    }

    @Bean
    ConnectToRoomRepository roomRepositoryConnection(RoomRepository roomRepository,
                                                     ScreeningRepository screeningRepository) {
        return new RoomRepositoryConnection(roomRepository, screeningRepository);
    }

    @Bean
    ConnectToMovieRepository movieRepositoryConnection(MovieRepository movieRepository,
                                                       ScreeningRepository screeningRepository) {
        return new MovieRepositoryConnection(movieRepository, screeningRepository);
    }

    @Bean
    ConnectToScreeningRepository screeningRepositoryConnection(ScreeningRepository screeningRepository) {
        return new ScreeningRepositoryConnection(screeningRepository);
    }

    @Bean
    ConnectToBookingRepository bookingRepositoryConnection(BookingRepository bookingRepository) {
        return new BookingRepositoryConnection(bookingRepository);
    }

}
