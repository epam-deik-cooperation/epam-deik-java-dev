package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.logsystem.LogSystem;
import com.epam.training.ticketservice.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Authenticator {
    private Admin admin;
    private LogSystem logSystem;

    @Autowired
    public Authenticator(Admin admin, LogSystem logSystem){
        this.admin = admin;
        this.logSystem = logSystem;
    }

    @ShellMethod(key = "sign in privileged", value = "Signs in")
    public String signIn(String name, String passwd){
        if (admin.isLogged()) {
            return "Already signed in.";
        } else if (logSystem.LogIn(name, passwd)) {
            return "Logged in as " + name;
        }
        else return "Login failed due to incorrect credentials.";
    }

    @ShellMethod(key = "sign out", value = "Signs out")
    public String signOut(){
        if(admin.isLogged()) {
            logSystem.LogOut();
            return "Successfully signed out";
        }else {
            return "Already signed out.";
        }
    }
}
