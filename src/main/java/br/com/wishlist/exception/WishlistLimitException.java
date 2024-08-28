package br.com.wishlist.exception;

public class WishlistLimitException extends RuntimeException {

    public WishlistLimitException(String message) {
        super(message);
    }

}