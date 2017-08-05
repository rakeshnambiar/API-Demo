@ReAlm
Feature:  To verify the Realm - API functionalities

  @TC01_RealmAPITest   @NoBrowser
  Scenario Outline: To verify the POST operation for various Test Data combinations
    Given I have Realm Service URL
    When I post the XML request with "<name>", "<description>"
    Then Service should returns the success code 201
    And Response should contain the mandatory tag values id, key
    And Requested name, description should available in the response

    Examples:
      | name   | description          |
      | RAKE   | String Testing       |
      | RAK123 | Alphanumeric Testing |
      | 123456 | Number Testing       |
      | 123RAK |                      |
      | $/#%@+ | Special Characters   |


  @TC02_RealmAPITest   @NoBrowser
  Scenario: To verify the duplicate Posting of a Realm
    Given I have Realm Service URL
    When I post the XML request with "DUPL01", "Duplicate Testing"
    Then Service should returns the success code 201
    When I try to make a POST Request again
    Then Service should return the error response 400

  @TC03_RealmAPITest   @NoBrowser
  Scenario: To verify the POST operation with invalid input
    Given I have Realm Service URL
    When I post the XML request with "MDT01", "Media Type Check" in JSON format
    Then Service should return the error response 415 for the unsupported media type

  @TC04_RealmAPITest   @NoBrowser
  Scenario Outline: To verify the GET operation for various Test Data combinations
    Given I have Realm Service URL
    When I successfully posted the XML request with "<name>", "<description>"
    And Retrieving the request which I have posted
    Then GET Response should contain the mandatory tag values id, key
    And Requested name, description should available in the GET response
    Examples:
      | name   | description          |
      | ABH    | String Testing       |
      | ABH999 | Alphanumeric Testing |
      | 999456 | Number Testing       |
      | AB99BH |                      |




    #Not scripted the GET Test case with id Not an integer

    #Not scripted the GET Test case with integer value more than 999 and this scenarios is failing