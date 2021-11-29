package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.model.Booking;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements UserInterface {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private Integer password;
    private boolean privileged;

    @OneToMany
    private List<Booking> bookings;

    protected User() {

    }

    public User(String username, String password, boolean privileged) {
        this.username = username;
        this.password = password.hashCode();
        this.privileged = privileged;
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Integer getPasswordHash() {
        return password;
    }

    @Override
    public boolean isPrivileged() {
        return privileged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User that = (User) o;
        return privileged == that.privileged && Objects.equals(username, that.username)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, privileged);
    }
}