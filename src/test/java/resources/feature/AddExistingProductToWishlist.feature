Feature: Add an existing product to the wishlist

  Scenario: Client tries to add a product that already exists in the wishlist
    When the client adds a duplicate product to the wishlist
    Then the client receives an error message about product already exists
    And the client receives status error about product already exists