package br.com.wishlist.service;

import br.com.wishlist.enums.WishlistErrorEnum;
import br.com.wishlist.exception.EntityNotFoundException;
import br.com.wishlist.exception.ProductAlreadyExistsException;
import br.com.wishlist.exception.WishlistLimitException;
import br.com.wishlist.model.dto.ProductDTO;
import br.com.wishlist.model.entity.Wishlist;
import br.com.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Value("${customer.wishlist.limit}")
    private int wishlistLimit;

    public Set<ProductDTO> getAllProducts(String userId) {
        final Wishlist wishlist = getWishlist(userId);

        return wishlist.getProducts();
    }

    private Wishlist getWishlist(String userId) {
        return wishlistRepository.findByUserId(userId).orElseThrow(
            () -> new EntityNotFoundException(String.format(WishlistErrorEnum.WISHLIST_NOT_FOUND.getMessage(), userId))
        );
    }

    public ProductDTO getProductById(String userId, String productId) {
        final Wishlist wishlist = getWishlist(userId);

        return wishlist.getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(WishlistErrorEnum.PRODUCT_NOT_FOUND.getMessage(), productId))
                );
    }

    public void addProduct(String userId, ProductDTO productDTO) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByUserId(userId);
        Wishlist wishlist = new Wishlist(userId);

        if (wishlistOptional.isPresent()) {
            wishlist = wishlistOptional.get();
            validateAddProduct(wishlist, productDTO);
        }

        wishlist.getProducts().add(productDTO);

        wishlistRepository.save(wishlist);
    }

    public void removeProduct(String userId, String productId) {
        final Wishlist wishlist = getWishlist(userId);

        boolean removed = wishlist.getProducts().removeIf(product -> product.getId().equals(productId));

        if (!removed)
            throw new EntityNotFoundException(String.format(WishlistErrorEnum.PRODUCT_NOT_FOUND.getMessage(), productId));

        wishlistRepository.save(wishlist);
    }

    private void validateAddProduct( Wishlist wishlist, ProductDTO productDTO ) {
        if (isExistProduct(wishlist, productDTO.getId())) {
            throw new ProductAlreadyExistsException(
                    String.format(WishlistErrorEnum.PRODUCT_ALREADY_EXISTS.getMessage(), productDTO.getId())
            );
        }

        if (wishlist.getProducts().size() >= wishlistLimit) {
            throw new WishlistLimitException(WishlistErrorEnum.WISHLIST_LIMIT_REACHED.getMessage());
        }
    }

    private boolean isExistProduct(Wishlist wishlist, String productId) {
        return wishlist.getProducts().stream().anyMatch(p -> p.getId().equals(productId));
    }

}
