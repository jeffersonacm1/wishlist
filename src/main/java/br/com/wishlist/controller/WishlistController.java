package br.com.wishlist.controller;

import br.com.wishlist.model.dto.ErrorMessageDTO;
import br.com.wishlist.model.dto.ProductDTO;
import br.com.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlists/user/{userId}")
@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @Operation(summary = "Get all products in user wishlist", description = "Return all products in user wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Wishlist with UserId not found")
    })
    @GetMapping("/products")
    public ResponseEntity<Set<ProductDTO>> getProducts(@PathVariable String userId) {
        return ResponseEntity.ok(wishlistService.getAllProducts(userId));
    }

    @Operation(summary = "Get user product by Id", description = "Return user wishlist product by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Wishlist with UserId not found or Product with Id not found"),
    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> isProductInWishlist(@PathVariable String userId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.getProductById(userId, productId));
    }

    @Operation(summary = "Add product to wishlish", description = "Add a new product to User wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successful added"),
            @ApiResponse(responseCode = "409", description = "Product with already exists in wishlist or Wishlist limit reached"),
    })
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@PathVariable String userId, @Valid @RequestBody ProductDTO productDTO) {
        wishlistService.addProduct(userId, productDTO);
        return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId()).toUri()).build();
    }

    @Operation(summary = "Remove product to wishlish", description = "Remove a product to wishlist by Product Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successful removed"),
            @ApiResponse(responseCode = "404", description = "Wishlist with UserId not found or Product with Id not found"),
    })
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable String userId, @PathVariable String productId) {
        wishlistService.removeProduct(userId, productId);
        return ResponseEntity.noContent().build();
    }

}
