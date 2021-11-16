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
    public void createPriceComponent(String name, int price) {
        try {
            priceComponentService.createPriceComponent(name, price);
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }


    @ShellMethod(value = "attach price component to movie componentName movieTitle",
            key = "attach price component to movie")
    @ShellMethodAvailability("isAccountAdmin")
    public void attachComponentToMovie(String componentName, String movieTitle) {
        try {
            priceComponentService.attachPriceComponentToMovie(componentName, movieTitle);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    @ShellMethod(value = "attach price component to room componentName roomName",
            key = "attach price component to room")
    @ShellMethodAvailability("isAccountAdmin")
    public void attachComponentToRoom(String componentName, String roomName) {

        try {
            priceComponentService.attachPriceComponentToRoom(componentName, roomName);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    @ShellMethod(value = "attach price component to screening componentName movieTitle roomName date",
            key = "attach price component to screening")
    @ShellMethodAvailability("isAccountAdmin")
    public void attachComponentToScreening(
            String componentName, String movieTitle, String roomName, String date) throws NotFoundException {
        priceComponentService.attachPriceComponentToScreening(componentName, movieTitle, roomName, date);
    }


}
