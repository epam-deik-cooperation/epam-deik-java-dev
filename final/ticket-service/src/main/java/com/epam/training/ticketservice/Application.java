package com.epam.training.ticketservice;


import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("ticket-service:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
    }
}
