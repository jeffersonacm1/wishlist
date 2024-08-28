Feature: Add a product to a full wishlist

  Scenario: Client tries to add a product to a wishlist that has reached its limit
    Given the wishlist already full
    When the client adds a product on full wishlist
    Then the client receives an error message about wishlist limit reached
    And the client receives a status about wishlist limit reached
