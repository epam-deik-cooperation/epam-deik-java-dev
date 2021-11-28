package hu.unideb.inf.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hu.unideb.inf.ticketservice")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
