Feature: Find a list of movies

  Scenario: Dan wants to find a list of movies for watching with his infant child

    Given Dan wants to find a list of movies up to a U rating
    When he requests a list of movies
    Then Dan gets a list of movies
    And it only includes U rated movies

  Scenario: Dan wants to find a list of movies for watching with his older child

    Given Dan wants to find a list of movies up to a PG rating
    When he requests a list of movies
    Then Dan gets a list of movies
    And it only includes U,PG rated movies

  Scenario: Dan wants to find a list of movies but has an invalid brand

    Given Dan wants to find a list of movies up to a Invalid_Rating rating
    When he requests a list of movies
    Then Dan gets an empty list of movies