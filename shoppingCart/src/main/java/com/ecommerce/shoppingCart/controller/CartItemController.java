package com.ecommerce.shoppingCart.controller;

import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.CartItem;
import com.ecommerce.shoppingCart.response.ApiResponse;
import com.ecommerce.shoppingCart.service.cart.cartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final cartItemService cartitemservice;
    public CartItemController(cartItemService cartitemservice) {
        this.cartitemservice = cartitemservice;
    }

    @PostMapping("addItem")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam int quantity) {
        try {
            cartitemservice.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("added successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("removeItem/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try {
            cartitemservice.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("updateItemQuentity/{cartId}/{productId}")
    public ResponseEntity<ApiResponse> updateItemQuentity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam int quantity) {
        try {
            cartitemservice.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
