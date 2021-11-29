package hu.unideb.inf.ticketservice.command.handler;

import hu.unideb.inf.ticketservice.command.impl.booking.BookScreeningCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class BookingCommandHandler {

    private final BookScreeningCommand bookScreeningCommand;

    @Autowired
    public BookingCommandHandler(BookScreeningCommand bookScreeningCommand) {
        this.bookScreeningCommand = bookScreeningCommand;
    }

    @ShellMethod(value = "Books a screening", key = "book")
    public String bookScreening(String movieName, String roomName, String date, String seats) {
        return bookScreeningCommand.execute(List.of(movieName,roomName,date,seats));
    }

}
