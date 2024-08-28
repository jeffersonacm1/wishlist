Feature: Remove a nonexistent product from the wishlist

  Scenario: Client tries to remove a product that does not exist in the wishlist
    When the client removes a product that does not exist in the wishlist
    Then the client receives an error message about product not found
    And the client receives a status code about product not found
