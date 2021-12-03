package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.UpdatePriceCommand;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.PriceService;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import hu.unideb.inf.ticketservice.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class TestUpdatePriceCommand {

    private UpdatePriceCommand underTest;
    private LoggedInUserTrackService userTrackService;
    private PriceService priceService;

    @BeforeEach
    public void setup() {
        userTrackService = new LoggedInUserTrackImpl(new Administrator(new AdminCredentialsProvider()));
        priceService = new PriceServiceImpl();
        underTest = new UpdatePriceCommand(priceService,userTrackService);
    }

    @Test
    public void testExecuteShouldNotUpdateBasePriceWhenSignedIntoNonPrivilegedAccount() {
        //Given
        userTrackService.updateCurrentUser(new DefaultUser());
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(Collections.emptyList());

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldUpdateBasePriceWhenSignedIntoPrivilegedAccount() {
        //Given
        final String expected = "Alright";
        final Integer expectedPrice = 2000;

        //When
        final String result = underTest.execute(List.of("2000"));
        final Integer resultPrice = priceService.getBasePrice();

        //Then
        Assertions.assertEquals(expected,result);
        Assertions.assertEquals(expectedPrice,resultPrice);
    }
}
