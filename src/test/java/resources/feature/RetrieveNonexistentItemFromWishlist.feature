Feature: Retrieve a nonexistent item from the wishlist

  Scenario: Client tries to retrieve a product that does not exist in the wishlist
    When the client try get a product that does not exist in the wishlist
    Then the client receives an error message about product not exists
    And the client receives a status code about product not exists
