Feature:google search page

  Scenario: I want to search Flabola INC. on google

    Given I am pointing to searchEngine.com

    When I search for input

    And <keyword : "Flabola FSQS">

    Then SEARCH is successful!
