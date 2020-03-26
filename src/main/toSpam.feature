Feature: Test spam functionality

  Scenario: Moving a mail to spam folder
    Given I am in inbox folder
    When I open a mail and press spam button
    Then mail is moved to spam folder