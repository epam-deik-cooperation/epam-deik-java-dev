package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AdminLogin {
    private final Admin admin;

    @Autowired
    public AdminLogin(Admin admin) {
        this.admin = admin;
    }

    @ShellMethod(key = "sign in privileged", value = "Signs in")
    public String signIn(String name, String password){
        if (name.equals(admin.getUsername()) && password.equals(admin.getPassword())){
            return "You are logged in.";
        }
        return "You are not logged in.";
    }
}
