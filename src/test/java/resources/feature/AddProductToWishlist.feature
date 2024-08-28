Feature: Add a product to the wishlist

  Scenario: Client adds a new product to the wishlist
    When the client adds a product to the wishlist
    Then the product is added to the wishlist successfully
    And the client receives a successful inclusion return status code