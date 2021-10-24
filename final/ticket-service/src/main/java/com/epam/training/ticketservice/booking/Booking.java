package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.Account;
import com.epam.training.ticketservice.screening.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Map<Integer, Integer> seats;

    @Column(name = "price")
    private int price;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        List<String> seatList = new ArrayList<>();

        seats.forEach((key, value) -> seatList.add(String.format("(%d,%d)", key, value)));

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
                .append(" HUF\n")
                .toString();

    }


}
