Feature: Retrieve all items from the wishlist

  Scenario: Client retrieves all items in the wishlist
    Given the wishlist with products
    When the client retrieves all items in the wishlist
    Then the client receives a list of all items in the wishlist
    And the client receives a success of get all items status code