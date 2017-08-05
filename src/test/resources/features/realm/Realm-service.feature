@ReAlm
Feature:  To verify the Realm - API functionalities

  @TC01_RealmAPITest   @NoBrowser
  Scenario Outline: To verify the POST operations for various Test Data combinations
    Given I have Realm Service URL
    When I post the XML request with "<name>", "<description>"
    Then Service should returns the success code 201
    And Response should contain the mandatory tag values id, key
    And Requested name, description should available in the response

    Examples:
      | name   | description           |
      | RAK    | String Testing        |
      | RAK123 | Alphanumeric Testing  |
      | 123456 | Number Testing        |
      | 123RAK |                       |



    #Try to post the duplicate code

      #GET negative scenarios as mentioned in the document