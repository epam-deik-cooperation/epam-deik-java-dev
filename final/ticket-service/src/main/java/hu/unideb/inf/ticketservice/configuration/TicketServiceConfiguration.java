package hu.unideb.inf.ticketservice.configuration;

import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.UserInterface;
import hu.unideb.inf.ticketservice.repository.UserRepository;
import hu.unideb.inf.ticketservice.repository.BookingRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.repository.SeatRepository;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.service.DateValidationService;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToUserRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToMovieRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRoomRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookingRepository;
import hu.unideb.inf.ticketservice.service.connection.impl.RoomRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.MovieRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.ScreeningRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.BookedSeatRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.BookingRepositoryConnection;
import hu.unideb.inf.ticketservice.service.connection.impl.UserRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.DateValidationImpl;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import hu.unideb.inf.ticketservice.service.impl.SeatValidator;
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
    SeatValidationService seatParserService(ConnectToBookedSeatRepository seatRepository) {
        return new SeatValidator(seatRepository);
    }

    @Bean
    ConnectToUserRepository userRepositoryConnection(UserRepository userRepository) {
        return new UserRepositoryConnection(userRepository);
    }

    @Bean
    ConnectToBookedSeatRepository bookedSeatRepositoryConnection(SeatRepository seatRepository) {
        return new BookedSeatRepositoryConnection(seatRepository);
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
