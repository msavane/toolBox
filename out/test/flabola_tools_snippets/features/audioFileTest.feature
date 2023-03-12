Feature: get audio data

  Scenario: I want to fetch information about data

    Given I am pointing to url

    When I post audio to AssemblyAI

    And <audioLocation : "https://www.poetryfoundation.org/play/75232">

    Then FETCH AUDIO INFO is successful!
