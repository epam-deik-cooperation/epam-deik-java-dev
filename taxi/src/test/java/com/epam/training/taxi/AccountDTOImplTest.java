package com.epam.training.taxi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

class AccountDTOImplTest {

    private final String PATH_TO_READ = "src/test/resources/testAccounts.csv";
    private final String PATH_TO_WRITE = "src/test/resources/testOut.csv";

    private AccountDTO underTest;

    @BeforeEach
    void setupClassInstance(){
        underTest = new AccountDTOImpl();
    }

    @Test
    void testReadFromCSVShouldGiveAListOfAccountsWhenGivenValidFilePath() throws IOException {
        //Given
        List<Account> excepted = List.of(
          new Account(1L,"Apple",0.2),
          new Account(2L,"Pear",0.0),
          new Account(3L,"Peach",0.8)
        );
        //When
        List<Account> actual = underTest.readFromCSV(PATH_TO_READ);
        //Then
        Assertions.assertEquals(excepted,actual);
    }

    @Test
    void testReadFromCSVShouldThrowIOExceptionWhenGivenNonValidFilePath(){
        //Given - none
        //When - Then
        Assertions.assertThrows(IOException.class, () ->
                underTest.readFromCSV(PATH_TO_READ+"/plusOneDirectory"));
    }

    @Nested
    public class AccountDTOImplTestWriteMethodes
    {
        @BeforeEach
        void setupClass() throws IOException {
            //Making sure the file does not exist before testing the write methods
            if(Files.exists(Path.of(PATH_TO_WRITE)))
            {
                Files.delete(Path.of(PATH_TO_WRITE));
            }
        }

        @Test
        void testWriteToCSVShouldWriteTheGivenTextWhenGivenTheCorrectFile() throws IOException {
            //Given
            String expected = "This is a test string";
            Scanner reader;
            String actual;

            //When
            underTest.writeToCSV(PATH_TO_WRITE,expected);

            //Then
            reader = new Scanner(new File(PATH_TO_WRITE));
            actual = reader.nextLine();
            reader.close();
            Assertions.assertEquals(expected,actual);
        }
        @Test
        void testWriteToCSVShouldAppendTheGivenTextWhenGivenTheCorrectFile() throws IOException {
            //Given
            String line1 = "This is the first line";
            String line2 = "This is the second line";
            String expected = line1 + line2;
            Scanner reader;
            String actual;

            //When
            underTest.writeToCSV(PATH_TO_WRITE,line1);
            underTest.writeToCSV(PATH_TO_WRITE,line2);

            //Then
            reader = new Scanner(new File(PATH_TO_WRITE));
            actual = reader.nextLine() + reader.nextLine();
            reader.close();
            Assertions.assertEquals(expected,actual);
        }
    }
}