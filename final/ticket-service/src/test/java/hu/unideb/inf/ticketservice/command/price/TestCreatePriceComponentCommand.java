package hu.unideb.inf.ticketservice.command.price;

import hu.unideb.inf.ticketservice.command.impl.price.CreatePriceComponentCommand;
import hu.unideb.inf.ticketservice.model.component.PriceComponent;
import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.repository.ComponentRepository;
import hu.unideb.inf.ticketservice.service.LoggedInUserTrackService;
import hu.unideb.inf.ticketservice.service.connection.impl.ComponentRepositoryConnection;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class TestCreatePriceComponentCommand {

    private CreatePriceComponentCommand underTest;
    private LoggedInUserTrackService userTrackService;
    @Mock
    private ComponentRepository componentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userTrackService = new LoggedInUserTrackImpl(new DefaultUser());
        underTest = new CreatePriceComponentCommand(userTrackService,
                new ComponentRepositoryConnection(componentRepository));
    }

    @Test
    public void testExecuteShouldNotCreatePriceComponentWhenNotSignedIntoPrivilegedAccount() {
        //Given
        final String expected = "Unauthorized action!";

        //When
        final String result = underTest.execute(Collections.emptyList());

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testExecuteShouldCreatePriceComponentWhenSignedIntoPrivilegedAccount() {
        //Given
        userTrackService.updateCurrentUser(new Administrator(new AdminCredentialsProvider()));
        final PriceComponent component = new PriceComponent("Component",1500);
        final String expected = "Alright";

        //When
        final String result = underTest.execute(List.of("Component","1500"));

        //Then
        Assertions.assertEquals(expected,result);
        Mockito.verify(componentRepository).save(component);
    }
}
