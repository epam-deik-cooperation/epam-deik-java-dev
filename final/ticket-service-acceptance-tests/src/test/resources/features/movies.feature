Feature: allows creating movie descriptions
  Background:
    Given the application is started
    And the prompt containing "Ticket service>" is printed

  @grade2-requirement
  Scenario: an admin user can create and list movies
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create movie Sátántangó drama 450" command
    And the user types the "list movies" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes)"

  @grade2-requirement
  Scenario: an admin user can update a movie
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create movie Sátántangó dram 450" command
    When the user types the "update movie Sátántangó drama 450" command
    And the user types the "list movies" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes)"

  @grade2-requirement
  Scenario: an admin user can delete a movie
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create movie Sátántangó drama 450" command
    When the user types the "delete movie Sátántangó" command
    And the user types the "list movies" command
    Then the next line of the output is "There are no movies at the moment"

  @grade2-requirement
  Scenario: an unauthenticated user can list movies
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create movie Sátántangó drama 450" command
    And the user types the "sign out" command
    When the user types the "list movies" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes)"

  @grade4-requirement
  Scenario: an authenticated, non-privileged user can list movies
    Given the user types the "sign in privileged admin admin" command
    And the user types the "create movie Sátántangó drama 450" command
    And the user types the "sign out" command
    And the user types the "sign up sanyi asdQWE123" command
    When the user types the "sign in sanyi asdQWE123" command
    And the user types the "list movies" command
    Then the next line of the output is "Sátántangó (drama, 450 minutes)"
