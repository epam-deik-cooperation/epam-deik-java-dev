package hu.unideb.inf.ticketservice.service.impl;

import hu.unideb.inf.ticketservice.model.Room;
import hu.unideb.inf.ticketservice.model.Seat;
import hu.unideb.inf.ticketservice.service.SeatValidationService;
import hu.unideb.inf.ticketservice.service.connection.ConnectToBookedSeatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatValidator implements SeatValidationService {

    private static final String ROW_COLUMN_DELIMITER = ",";
    private static final String SEAT_DELIMITER = " ";

    private final ConnectToBookedSeatRepository seatRepository;

    public SeatValidator(ConnectToBookedSeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getSeats(String input, Room room) {
        List<Seat> result = new ArrayList<>();
        String[] seatPairs = input.split(SEAT_DELIMITER);
        for (String seatPair : seatPairs) {
            String[] seat = seatPair.split(ROW_COLUMN_DELIMITER);
            Seat seatObj = new Seat(Integer.valueOf(seat[0]), Integer.valueOf(seat[1]), room);
            result.add(seatObj);
        }
        return result;
    }

    @Override
    public Seat isAlreadyBookedSeat(List<Seat> seats, Room room) {
        List<Seat> bookedSeatList = seatRepository.getAllSeats();
        bookedSeatList = filterByRoom(bookedSeatList, room);
        for (Seat bookedSeat : bookedSeatList) {
            for (Seat givenSeat : seats) {
                if (bookedSeat.equals(givenSeat)) {
                    return bookedSeat;
                }
            }
        }
        return null;
    }

    @Override
    public Seat isSeatValid(List<Seat> seats, Room room) {
        for (Seat seat : seats) {
            if (isBetween(seat.getRowNumber(), room.getNumberOfRows(), 0)
                && isBetween(seat.getColumnNumber(), room.getNumberOfColumns(), 0)) {
                return null;
            } else {
                return seat;
            }
        }
        return null;
    }


    private List<Seat> filterByRoom(List<Seat> bookedSeatList, Room room) {
        return bookedSeatList.stream()
                .filter(s -> s.getRoom().equals(room))
                .collect(Collectors.toList());
    }

    private boolean isBetween(Integer toBeChecked, Integer max, Integer min) {
        return toBeChecked <= max && toBeChecked >= min;
    }
}
