Feature: Pepsi cans

  Scenario: Counting pepsi cans
    Given I have <opening balance> pepsi cans
    And I have drunk <processed> pepsi cans
    When I go to my fridge
    Then I should have <in stock> pepsi cans