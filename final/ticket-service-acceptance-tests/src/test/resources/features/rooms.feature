Feature: allow creating, reading, updating and deleting rooms where the screenings will happen

  Background:
    Given the application is started
    And the prompt containing "Ticket service>" is printed

  @grade2-requirement
  Scenario: an admin user can create and list rooms
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create room Pedersoli 20 10" command
    And the user types the "list rooms" command
    Then the next line of the output is "Room Pedersoli with 200 seats, 20 rows and 10 columns"

  @grade2-requirement
  Scenario: an admin user can update a room
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    When the user types the "update room Pedersoli 10 10" command
    And the user types the "list rooms" command
    Then the next line of the output is "Room Pedersoli with 100 seats, 10 rows and 10 columns"

  @grade2-requirement
  Scenario: an admin user can delete a room
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    When the user types the "delete room Pedersoli" command
    And the user types the 'list rooms' command
    Then the next line of the output is "There are no rooms at the moment"

  @grade2-requirement
  Scenario: an unauthenticated user can list rooms
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    And the user types the "sign out" command
    When the user types the "list rooms" command
    Then the next line of the output is "Room Pedersoli with 200 seats, 20 rows and 10 columns"

  @grade4-requirement
  Scenario: an authenticated, non-privileged user can list rooms
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    And the user types the "sign out" command
    And the user types the "sign up sanyi asdQWE123" command
    When the user types the "sign in sanyi asdQWE123" command
    And the user types the "list rooms" command
    Then the next line of the output is "Room Pedersoli with 200 seats, 20 rows and 10 columns"
