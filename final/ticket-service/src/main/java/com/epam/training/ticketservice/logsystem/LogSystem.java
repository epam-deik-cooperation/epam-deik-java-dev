package com.epam.training.ticketservice.logsystem;

import com.epam.training.ticketservice.users.Admin;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class LogSystem {
    final private Admin admin;

    public boolean LogIn(String name, String passwd){
        boolean logged = admin.getUsername().equals(name) && admin.getPassword().equals(passwd);
        admin.setLogged(logged);
        return logged;
    }

    public boolean LogOut(){
        admin.setLogged(false);
        return false;
    }
}
