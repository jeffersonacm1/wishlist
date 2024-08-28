package br.com.wishlist.enums;

import lombok.Getter;

@Getter
public enum WishlistErrorEnum {

    PRODUCT_ALREADY_EXISTS("Product with Id '%s' already exists in wishlist"),
    PRODUCT_NOT_FOUND("Product with Id '%s' not found in wishlist"),
    WISHLIST_NOT_FOUND("Wishlist with UserId '%s' not found"),
    WISHLIST_LIMIT_REACHED("Wishlist limit reached");

    private final String message;

    WishlistErrorEnum(String message) {
        this.message = message;
    }

}
