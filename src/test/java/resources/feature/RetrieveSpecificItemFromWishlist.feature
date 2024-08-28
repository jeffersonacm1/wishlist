Feature: Retrieve a specific item from the wishlist

  Scenario: Client retrieves a specific item in the wishlist
    When the client retrieves a specific product from the wishlist
    Then the client receives the item details
    And the client receives a success codestatus of get the item
