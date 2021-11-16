package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.exception.AlreadyExistsException;
import com.epam.training.ticketservice.movie.Movie;
import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.room.Room;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.Screening;
import com.epam.training.ticketservice.screening.ScreeningService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceComponentService {

    private static final String NO_PRICE_COMPONENT_FOUND = "No price component found with such name!";

    private final PriceComponentRepository priceComponentRepository;
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;

    @Transactional
    public int getAdditionalPrice(Screening screening) {
        int price = 0;

        List<PriceComponent> components = priceComponentRepository.findAll();

        Optional<PriceComponent> screeningComponent = components.stream()
                .filter(x -> x.getScreenings().contains(screening))
                .findFirst();

        Optional<PriceComponent> roomComponent = components.stream()
                .filter(x -> x.getRooms().contains(screening.getRoom()))
                .findFirst();

        Optional<PriceComponent> movieComponent = components.stream()
                .filter(x -> x.getMovies().contains(screening.getMovie()))
                .findFirst();

        if (roomComponent.isPresent()) {
            price += roomComponent.get().getPrice();
        }
        if (movieComponent.isPresent()) {
            price += movieComponent.get().getPrice();
        }
        if (screeningComponent.isPresent()) {
            price += screeningComponent.get().getPrice();
        }

        return price;
    }


    public void createPriceComponent(String name, int price) throws AlreadyExistsException {
        if (priceComponentRepository.existsByNameContainingIgnoreCase(name)) {
            throw new AlreadyExistsException("Component already exists with such name!");
        } else {
            priceComponentRepository.save(PriceComponent.builder()
                    .name(name)
                    .price(price)
                    .build());
        }
    }

    @Transactional
    public void attachPriceComponentToMovie(String componentName, String movieTitle) throws NotFoundException {

        PriceComponent priceComponent = priceComponentRepository.getByNameContainingIgnoreCase(componentName);

        if (priceComponent == null) {
            throw new NotFoundException(NO_PRICE_COMPONENT_FOUND);
        } else {
            List<Movie> movies = priceComponent.getMovies();
            if (movies == null) {
                movies = new ArrayList<>();
            }

            movies.add(movieService.findByTitle(movieTitle));
            priceComponent.setMovies(movies);

            priceComponentRepository.save(priceComponent);
        }
    }


    @Transactional
    public void attachPriceComponentToScreening(
            String componentName, String movieTitle, String roomName, String date) throws NotFoundException {

        PriceComponent priceComponent = priceComponentRepository.getByNameContainingIgnoreCase(componentName);

        if (priceComponent == null) {
            throw new NotFoundException(NO_PRICE_COMPONENT_FOUND);
        } else {
            List<Screening> screenings = priceComponent.getScreenings();
            if (screenings == null) {
                screenings = new ArrayList<>();
            }

            screenings.add(screeningService.getScreeningByProperties(movieTitle, roomName, date));
            priceComponent.setScreenings(screenings);

            priceComponentRepository.save(priceComponent);
        }
    }

    @Transactional
    public void attachPriceComponentToRoom(String componentName, String roomName) throws NotFoundException {

        PriceComponent priceComponent = priceComponentRepository.getByNameContainingIgnoreCase(componentName);

        if (priceComponent == null) {
            throw new NotFoundException(NO_PRICE_COMPONENT_FOUND);
        } else {
            List<Room> rooms = priceComponent.getRooms();
            if (rooms == null) {
                rooms = new ArrayList<>();
            }

            rooms.add(roomService.findByName(roomName));
            priceComponent.setRooms(rooms);

            priceComponentRepository.save(priceComponent);
        }
    }
}
