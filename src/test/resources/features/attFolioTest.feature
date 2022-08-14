Feature:attribfolio1

  Scenario: I want to fetch folio consecutif

    Given I am pointing to url.com

    When I click reserver

    And <debut : "int">
    And <end : "int">

    Then FETCH FOLIO CONSECUTIF is successful!
