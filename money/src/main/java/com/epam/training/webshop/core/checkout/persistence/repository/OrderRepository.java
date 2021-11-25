package com.epam.training.webshop.core.checkout.persistence.repository;

import com.epam.training.webshop.core.checkout.persistence.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUsername(String username);
}
