package hu.unideb.inf.ticketservice.service;

import hu.unideb.inf.ticketservice.model.user.Administrator;
import hu.unideb.inf.ticketservice.model.user.DefaultUser;
import hu.unideb.inf.ticketservice.model.user.User;
import hu.unideb.inf.ticketservice.service.impl.AdminCredentialsProvider;
import hu.unideb.inf.ticketservice.service.impl.LoggedInUserTrackImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLoggedInUserTrackServiceImpl {

    private LoggedInUserTrackImpl underTest;

    @BeforeEach
    public void setup() {
        underTest = new LoggedInUserTrackImpl(new DefaultUser());
    }

    @Test
    public void testGetCurrentUserShouldReturnDefaultUserWhenNoOneIsLoggedIn() {
        //Given
        final User expected = new DefaultUser();

        //When
        final User result = underTest.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testUpdateCurrentUserShouldUpdateToAdministratorWhenGivenAdministratorUser() {
        //Given
        final User expected = new Administrator(new AdminCredentialsProvider());

        //When
        underTest.updateCurrentUser(new Administrator(new AdminCredentialsProvider()));
        final User result = underTest.getCurrentUser();

        //Then
        Assertions.assertEquals(expected,result);
    }
}
