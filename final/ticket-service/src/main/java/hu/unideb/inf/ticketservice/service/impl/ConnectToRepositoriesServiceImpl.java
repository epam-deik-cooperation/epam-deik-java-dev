package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.Movie;
import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Screening;
import hu.unideb.inf.ticketservice.repository.MovieRepository;
import hu.unideb.inf.ticketservice.repository.RoomRepository;
import hu.unideb.inf.ticketservice.repository.ScreeningRepository;
import hu.unideb.inf.ticketservice.service.connection.ConnectToRepositoriesService;

import java.util.List;

public class ConnectToRepositoriesServiceImpl implements ConnectToRepositoriesService {

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    public ConnectToRepositoriesServiceImpl(MovieRepository movieRepository,
                                            RoomRepository roomRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    @Override
    public void createMovie(Movie movie) {
        List<Movie> movies = listMovies();
        if (!(movies.contains(movie))) {
            movieRepository.save(movie);
        }
    }

    @Override
    public void updateMovie(String name, Movie movie) {
        movieRepository.updateByName(movie.getGenre(),movie.getNumberOfMinutes(),movie.getName());
    }

    @Override
    public void deleteMovie(String name) {
        List<Screening> screeningList = listScreenings();
        for (Screening s : screeningList) {
            if (s.getMovie().getName().equals(name)) {
                screeningRepository.delete(s.getRoom(),s.getMovie(),s.getScreeningDate());
            }
        }
        movieRepository.deleteByName(name);
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void createRoom(Room room) {
        List<Room> rooms = listRooms();
        if (!(rooms.contains(room))) {
            roomRepository.save(room);
        }
    }

    @Override
    public void deleteRoom(String name) {
        List<Screening> screeningList = listScreenings();
        for (Screening s : screeningList) {
            if (s.getRoom().getName().equals(name)) {
                screeningRepository.delete(s.getRoom(),s.getMovie(),s.getScreeningDate());
            }
        }
        roomRepository.deleteByName(name);
    }

    @Override
    public void updateRoom(String name, Room room) {
        roomRepository.updateByName(room.getNumberOfSeats(), room.getNumberOfRows(),
                room.getNumberOfColumns(), room.getName());
    }

    @Override
    public List<Screening> listScreenings() {
        return screeningRepository.findAll();
    }

    @Override
    public void createScreening(Screening screening) {
        List<Screening> screenings = listScreenings();
        if (!(screenings.contains(screening))) {
            screeningRepository.save(screening);
        }
    }

    @Override
    public void deleteScreening(Screening screening) {
        screeningRepository.delete(screening.getRoom(),screening.getMovie(),screening.getScreeningDate());
    }
}