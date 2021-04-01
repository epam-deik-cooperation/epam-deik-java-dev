Feature: allow creating accounts and logging in

  Background:
    Given the application is started
    And the prompt containing "Ticket service>" is printed

  @grade2-requirement
  Scenario: the admin account exists by default and can be logged in with the correct password
    When the user types the "sign in privileged admin admin" command
    And the user types the "describe account" command
    Then the next line of the output is "Signed in with privileged account 'admin'"

  @grade2-requirement
  Scenario: the admin account can be signed out
    Given the user types the "sign in privileged admin admin" command
    When the user types the "sign out" command
    And the user types the "describe account" command
    Then the next line of the output is "You are not signed in"

  @grade2-requirement
  Scenario: the admin account can not be logged in with incorrect password
    When the user types the "sign in privileged admin asdQWE123" command
    Then the next line of the output is "Login failed due to incorrect credentials"
    When the user types the "describe account" command
    Then the next line of the output is "You are not signed in"

  @grade4-requirement
  Scenario: non-privileged accounts can be created and logged in with the correct password
    When the user types the "sign up sanyi asdQWE123" command
    And the user types the "sign in sanyi asdQWE123" command
    And the user types the "describe account" command
    Then the next line of the output is "Signed in with account 'sanyi'"
    And the next line of the output is "You have not booked any tickets yet"

  @grade4-requirement
  Scenario: non-privileged accounts can not be logged in with the incorrect password
    Given the user types the "sign up sanyi asdQWE123" command
    When the user types the "sign in sanyi alma" command
    Then the next line of the output is "Login failed due to incorrect credentials"
    When the user types the "describe account" command
    Then the next line of the output is "You are not signed in"

  @grade4-requirement
  Scenario: non-privileged accounts can be signed out
    Given the user types the "sign up sanyi asdQWE123" command
    And the user types the "sign in sanyi asdQWE123" command
    When the user types the "sign out" command
    And the user types the "describe account" command
    Then the next line of the output is "You are not signed in"