Feature: allows creating, deleting and listing screenings
  Background: 
    Given the application is started
    And the prompt containing "Ticket service>" is printed
    And the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    And the user types the "create room Girotti 10 10" command
    And the user types the "create movie Sátántangó drama 450" command
    And the user types the "create movie \"Spirited Away\" animation 125" command
    And the user types the "sign out" command

  @grade2-requirement
  Scenario: The admin user can create and query screenings
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create screening Sátántangó Pedersoli \"2021-03-15 10:45\"" command
    And the user types the "create screening \"Spirited Away\" Pedersoli \"2021-03-14 16:00\"" command
    And the user types the "list screenings" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2021-03-15 10:45"
    And the next line of the output is "Spirited Away (animation, 125 minutes), screened in room Pedersoli, at 2021-03-14 16:00"

  @grade2-requirement
  Scenario: The admin can not create overlapping screenings in the same room
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 10:45\"" command
    When the user types the "create screening \"Spirited Away\" Pedersoli \"2021-03-15 10:50\"" command
    Then the next line of the output is "There is an overlapping screening"
    When the user types the "list screenings" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2021-03-15 10:45"

  @grade2-requirement
  Scenario: The admin can create overlapping screenings in different rooms
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 10:45\"" command
    When the user types the "create screening \"Spirited Away\" Girotti \"2021-03-15 10:50\"" command
    When the user types the "list screenings" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2021-03-15 10:45"
    And the next line of the output is "Spirited Away (animation, 125 minutes), screened in room Girotti, at 2021-03-15 10:50"

  @grade2-requirement
  Scenario: The admin can not create screenings during a 10 minute long breaks after another screening in the same room
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 11:00\"" command
    When the user types the "create screening \"Spirited Away\" Pedersoli \"2021-03-15 18:39\"" command
    Then the next line of the output is "This would start in the break period after another screening in this room"
    When the user types the "list screenings" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2021-03-15 11:00"

  @grade2-requirement
  Scenario: The admin can create screenings even if there is a break after a screening in a different room
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 11:00\"" command
    When the user types the "create screening \"Spirited Away\" Girotti \"2021-03-15 18:39\"" command
    And the user types the "list screenings" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2021-03-15 11:00"
    And the next line of the output is "Spirited Away (animation, 125 minutes), screened in room Girotti, at 2021-03-15 18:39"

  @grade2-requirement
  Scenario: The admin user can delete screenings
    Given the user types the "sign in privileged admin admin" command
    And  the user types the "create screening \"Spirited Away\" Pedersoli \"2021-03-14 16:00\"" command
    When the user types the "delete screening \"Spirited Away\" Pedersoli \"2021-03-14 16:00\"" command
    And the user types the "list screenings" command
    Then the next line of the output is "There are no screenings"