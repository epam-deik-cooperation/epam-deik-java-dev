Feature: allows users to book tickets for screenings, and show booking history

  Background:
    Given the application is started
    And the prompt containing "Ticket service>" is printed
    And the user types the "sign up sanyi asdQWE123" command
    And the user types the "sign up laci asdQWE123" command
    And the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    And the user types the "create room Girotti 10 10" command
    And the user types the "create movie Sátántangó drama 450" command
    And the user types the "create movie \"Spirited Away\" animation 125" command
    And the user types the "create screening \"Spirited Away\" Girotti \"2021-03-15 10:00\"" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 10:45\"" command
    And the user types the "sign out" command

  @grade4-requirement
  Scenario: an authenticated user can book a ticket for a screening
    Given the user types the "sign in sanyi asdQWE123" command
    When the user types the "book Sátántangó Pedersoli \"2021-03-15 10:45\" \"5,5 5,6\"" command
    Then the next line of the output is "Seats booked: (5,5), (5,6); the price for this booking is 3000 HUF"
    When the user types the "describe account" command
    Then the next line of the output is "Signed in with account 'sanyi'"
    And the next line of the output is "Your previous bookings are"
    And the next line of the output is "Seats (5,5), (5,6) on Sátántangó in room Pedersoli starting at 2021-03-15 10:45 for 3000 HUF"

  @grade4-requirement
  Scenario: an authenticated user can not book a ticket if at least one of the seats is already taken
    Given the user types the "sign in sanyi asdQWE123" command
    And the user types the "book Sátántangó Pedersoli \"2021-03-15 10:45\" \"5,5\"" command
    And the user types the "sign out" command
    And the user types the "sign in laci asdQWE123" command
    When the user types the "book Sátántangó Pedersoli \"2021-03-15 10:45\" \"5,5 5,6\"" command
    Then the next line of the output is "Seat (5,5) is already taken"
    When the user types the "describe account" command
    Then the next line of the output is "Signed in with account 'laci'"
    And the next line of the output is "You have not booked any tickets yet"

  @grade5-requirement
  Scenario: the price of the ticket in the history should not change if pricing changes after the booking
    Given the user types the "sign in sanyi asdQWE123" command
    And the user types the "book Sátántangó Pedersoli \"2021-03-15 10:45\" \"5,5\"" command
    And the user types the "sign out" command
    And the user types the "sign in privileged admin admin" command
    And the user types the "update base price 5000" command
    And the user types the "sign out" command
    And the user types the "sign in sanyi asdQWE123" command
    When the user types the "describe account" command
    Then the next line of the output is "Signed in with account 'sanyi'"
    And the next line of the output is "Your previous bookings are"
    And the next line of the output is "Seats (5,5) on Sátántangó in room Pedersoli starting at 2021-03-15 10:45 for 1500 HUF"