package hu.unideb.inf.ticketservice.model.user;

import hu.unideb.inf.ticketservice.model.Booking;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    protected String username;
    protected Integer password;
    protected boolean privileged;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    protected User() {

    }

    public User(String username, String password, boolean privileged) {
        this.username = username;
        this.password = password.hashCode();
        this.privileged = privileged;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getUsername() {
        return username;
    }

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