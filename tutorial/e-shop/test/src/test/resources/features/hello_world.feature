Feature: Hello World
  It should be run "Hello World"

  Scenario: Name as a parameter
    Given a "<name>"
    When generate sentence
    Then it should be "<answer>"

  Example:
  | name  | answer      |
  | World | Hello World |


