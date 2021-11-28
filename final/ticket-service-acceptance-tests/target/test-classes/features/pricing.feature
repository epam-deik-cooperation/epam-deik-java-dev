Feature: allows creating pricing components and assigning them to rooms, movies and screenings

  Background:
    Given the application is started
    And the prompt containing "Ticket service>" is printed
    And the user types the "sign in privileged admin admin" command
    And the user types the "create room Pedersoli 20 10" command
    And the user types the "create room Girotti 10 10" command
    And the user types the "create movie Sátántangó drama 450" command
    And the user types the "create movie \"Spirited Away\" aminmation 125" command
    And the user types the "create movie \"Pulp Fiction\" drama 154" command
    And the user types the "create screening Sátántangó Pedersoli \"2021-03-15 10:45\"" command
    And the user types the "create screening \"Spirited Away\" Pedersoli \"2021-03-14 16:00\"" command
    And the user types the "create screening \"Pulp Fiction\" Girotti \"2021-03-14 16:00\"" command
    And the user types the "sign out" command

  @grade5-requirement
  Scenario: a default per seat per screening price is defined to be 1500 HUF and is used in the calculation of the price of each seat
    When the user types the "show price for Sátántangó Pedersoli \"2021-03-15 10:45\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1500 HUF"

  @grade5-requirement
  Scenario: the admin user can change the default per seat per screening price
    Given the user types the "sign in privileged admin admin" command
    When the user types the "update base price 1000" command
    And the user types the "show price for Sátántangó Pedersoli \"2021-03-15 10:45\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1000 HUF"

  @grade5-requirement
  Scenario: the admin user can create price component and assign it to a movie
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create price component additionalFeeForSatantango 100" command
    And the user types the "attach price component to movie additionalFeeForSatantango Sátántangó" command
    And the user types the "show price for Sátántangó Pedersoli \"2021-03-15 10:45\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1600 HUF"
    When the user types the "show price for \"Spirited Away\" Pedersoli \"2021-03-14 16:00\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1500 HUF"

  @grade5-requirement
  Scenario: the admin user can create price component and assign it to a room
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create price component additionalFeeForPedersoli 100" command
    And the user types the "attach price component to room additionalFeeForPedersoli Pedersoli" command
    And the user types the "show price for Sátántangó Pedersoli \"2021-03-15 10:45\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1600 HUF"
    When the user types the "show price for \"Pulp Fiction\" Girotti \"2021-03-14 16:00\" 5,5" command
    Then the next line of the output is "The price for this booking would be 1500 HUF"

  @grade5-requirement
  Scenario: the admin user can create price component and assign it to a screening
    Given the user types the "sign in privileged admin admin" command
    When the user types the "create price component additionalFeeForPulpFictionScreening 100" command
    And the user types the "attach price component to screening additionalFeeForPulpFictionScreening \"Pulp Fiction\" Girotti \"2021-03-14 16:00\"" command
    And the user types the "show price for \"Pulp Fiction\" Girotti \"2021-03-14 16:00\" 5,5" command
    Then the next line of the output is "The price for this booking would be 1600 HUF"
    When the user types the "show price for Sátántangó Pedersoli \"2021-03-15 10:45\" 10,5" command
    Then the next line of the output is "The price for this booking would be 1500 HUF"