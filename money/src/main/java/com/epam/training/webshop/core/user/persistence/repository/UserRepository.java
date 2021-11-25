package com.epam.training.webshop.core.user.persistence.repository;

import com.epam.training.webshop.core.user.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
