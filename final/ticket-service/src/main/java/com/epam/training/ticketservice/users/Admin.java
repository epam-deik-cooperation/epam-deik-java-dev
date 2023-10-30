package com.epam.training.ticketservice.users;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Admin {
    private String username = "admin";
    private String password = "admin";
    private boolean logged = false;
}
