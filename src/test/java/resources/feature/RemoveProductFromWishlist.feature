Feature: Remove a product from the wishlist

  Scenario: Client removes an existing product from the wishlist
    When the client removes a product from the wishlist
    Then the product is removed from the wishlist successfully
    And the client receives a success remove status code
