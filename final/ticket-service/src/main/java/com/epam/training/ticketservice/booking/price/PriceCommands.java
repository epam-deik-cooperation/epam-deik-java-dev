package com.epam.training.ticketservice.booking.price;

import com.epam.training.ticketservice.SecuredCommands;
import com.epam.training.ticketservice.exception.AlreadyExistsException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class PriceCommands extends SecuredCommands {

    private final PriceComponentService priceComponentService;

    @ShellMethod(value = "update base price value", key = "update base price")
    @ShellMethodAvailability("isAccountAdmin")
    public void updateBasePrice(int value) {
        PriceCalculator.setBasePrice(value);
    }


    @ShellMethod(value = "create price component componentName price", key = "create price component")
    @ShellMethodAvailability("isAccountAdmin")
    public String createPriceComponent(String name, int price) {
        try {
            priceComponentService.createPriceComponent(name, price);
        } catch (AlreadyExistsException e) {
            return e.getMessage();
        }
        return String.format("Component '%s' successfully created for '%d'", name, price);
    }


    @ShellMethod(value = "attach price component to movie componentName movieTitle",
            key = "attach price component to movie")
    @ShellMethodAvailability("isAccountAdmin")
    public String attachComponentToMovie(String componentName, String movieTitle) {
        try {
            priceComponentService.attachPriceComponentToMovie(componentName, movieTitle);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Component '%s' successfully attachet to movie '%s'", componentName, movieTitle);
    }


    @ShellMethod(value = "attach price component to room componentName roomName",
            key = "attach price component to room")
    @ShellMethodAvailability("isAccountAdmin")
    public String attachComponentToRoom(String componentName, String roomName) {

        try {
            priceComponentService.attachPriceComponentToRoom(componentName, roomName);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Component '%s' successfully attachet to room '%s'", componentName, roomName);
    }


    @ShellMethod(value = "attach price component to screening componentName movieTitle roomName date",
            key = "attach price component to screening")
    @ShellMethodAvailability("isAccountAdmin")
    public String attachComponentToScreening(
            String componentName, String movieTitle, String roomName, String date) {
        try {
            priceComponentService.attachPriceComponentToScreening(componentName, movieTitle, roomName, date);
        } catch (NotFoundException e) {
            return e.getMessage();
        }
        return String.format("Component '%s' successfully attachet to screening '%s %s %s'",
                componentName, movieTitle, roomName, date);
    }


}
