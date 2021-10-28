package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.room.Seat;
import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private Screening screening;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Seat> seats;

    @Column(name = "price")
    private int price;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        List<String> seatList = new ArrayList<>();

        seats.forEach(x -> seatList.add(String.format("(%d,%d)", x.getRowIndex(), x.getColumnIndex())));

        return sb.append("Seats ")
                .append(String.join(", ", seatList))
                .append(" on ")
                .append(screening.getMovie().getTitle())
                .append(" in room ")
                .append(screening.getRoom().getName())
                .append(" starting at ")
                .append(screening.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .append(" for ")
                .append(price)
                .append(" HUF")
                .toString();

    }


}
