Feature:login page

  Scenario: I want to land on home page

    Given I am pointing to mysite.com

    When I click on log on button

    And <username : string>
    And <password : string>

    Then LAND ON HOME PAGE is successful!
