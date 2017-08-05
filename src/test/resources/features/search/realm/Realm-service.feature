@ReAlm
Feature:  To verify the Realm - API functionalities

  @TC01_RealmAPITest
  Scenario Outline: To verify the create, get & delete operations for various Test Data comninations
    Given I have Realm Service URL
    When I post the XML request with "<name>", "<description>"
    Then Service should returns success "<postResponseCode>"
    And Response should contain the mandatory tag values id, "<name>, "<description>", key

    Examples:
      |name    |description    |postResponseCode|
      |ab12    | Abhay Testing |                |
