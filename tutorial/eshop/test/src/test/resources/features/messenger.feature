@MessengerFeature
Feature: Messenger
  It has to generate a message

  Scenario Outline: Generate greeting message
    Given <speaker> is talking to <listener>
    When It generate <message>
    Then It should be <result>
    Examples:
      | speaker       | listener       | message       | result                |
      | James   ,true | William ,true  | Hello         | James:Hello William   |
      | William ,true | James   ,true  | Hello         | William:Hello James   |
      | James   ,true | William ,false | How are you ? | James:How are you ?   |
      | William ,true | James   ,false | I am good     | William:I am good     |
      | James   ,true | William ,true  | Goodbye       | James:Goodbye William |
      | William ,true | James   ,false | Goodbye       | William:Goodbye       |
