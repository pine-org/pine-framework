@MessengerFeature
Feature: Messenger
  It has to generate a message

  Scenario Outline: Generate greeting message
    Given <speaker> is talking to <listener>
    When It generate <message>
    Then It should be <result>
    Examples:
      | speaker       | listener       | message       | result                  |
      | James   ,true | William ,false | Hello         | James:Hello             |
      | William ,true | James   ,false | Hello         | William:Hello           |
      | James   ,true | William ,false | How are you ? | James:How are you ?     |
      | William ,true | James   ,false | I am good     | William:I am good       |
      | James   ,true | William ,false | Goodbye       | James:Goodbye           |
      | William ,true | James   ,true  | Goodbye       | William > James:Goodbye |
