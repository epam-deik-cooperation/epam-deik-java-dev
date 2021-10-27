package com.epam.training.taxi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainShouldThrowExceptionWhenNotGivenArguments() {
        //Given
        String[] arguments = new String[]{};
        //When - Then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                Main.main(arguments));
    }
}