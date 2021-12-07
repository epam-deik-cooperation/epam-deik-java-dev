package com.epam.training.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        //Workaround for the illegal argument exception thrown when trying to register both exit and quit
        var arguments = StringUtils.concatenateStringArrays(args,
                new String[]{"--spring.shell.command.quit.enabled=false"});
        SpringApplication.run(Application.class, arguments);
    }
}
