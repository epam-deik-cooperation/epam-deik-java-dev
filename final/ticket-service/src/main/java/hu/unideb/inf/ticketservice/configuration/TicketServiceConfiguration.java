package hu.unideb.inf.ticketservice.configuration;

import hu.unideb.inf.ticketservice.model.user.AbstractUser;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.DateValidationService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;
import hu.unideb.inf.ticketservice.service.impl.ConnectToRepositoriesServiceImpl;
import hu.unideb.inf.ticketservice.service.impl.DateValidationImpl;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketServiceConfiguration {
    @Bean
    public LoggedInUserTrackImpl loggedInUserTrackImpl(AbstractUser defaultUser) {
        return new LoggedInUserTrackImpl(defaultUser);
    }

    @Bean
    public AbstractUser defaultUser() {
        return new DefaultUser();
    }

    @Bean
    ConnectToRepositoriesService connectToRepositories(MovieRepository movieRepository, RoomRepository roomRepository,
                                                       ScreeningRepository screeningRepository) {
        return new ConnectToRepositoriesServiceImpl(movieRepository, roomRepository, screeningRepository);
    }

    @Bean
    DateValidationService dateValidationService() {
        return new DateValidationImpl();
    }
}
