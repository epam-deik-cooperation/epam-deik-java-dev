package hu.unideb.inf.ticketservice.repository;

import hu.unideb.inf.ticketservice.model.user.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User,Long> {

    List<User> findAll();

    void save(User user);
}
