Feature: SIM Card Activation
  As a customer service representative
  I want to activate SIM cards for customers
  So that they can start using their mobile service

  Scenario: Successfully activate a SIM card
    Given I have a valid ICCID "1255789453849037777"
    And I have the customer email "customer@email.com"
    When I submit an activation request
    Then the activation request should be successful
    And when I query the activation status with ID 1
    Then I should see the SIM card is active

  Scenario: Failed activation for invalid SIM card
    Given I have a valid ICCID "8944500102198304826"
    And I have the customer email "customer@email.com"
    When I submit an activation request
    Then the activation request should be unsuccessful
    And when I query the activation status with ID 2
    Then I should see the SIM card is inactive
