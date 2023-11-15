package com.epam.training.webshop.core.checkout.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public OrderItem(String name) {
        this.name = name;
    }
}
