package com.ecommerce.shoppingCart.controller;

import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Cart;
import com.ecommerce.shoppingCart.response.ApiResponse;
import com.ecommerce.shoppingCart.service.cart.cartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final cartService cartservice;
    public CartController(cartService cartservice) {
        this.cartservice = cartservice;
    }


    @GetMapping("cart/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable long cartId){
        try {
            Cart c = cartservice.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("successfully",c));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("not found ",null));
        }
    }

    @DeleteMapping("delete/{cartId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable long cartId){
        try {
            cartservice.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("successfully cleared",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("not found ",null));
        }
    }

    @GetMapping("totalPrice/{cartId}")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable long cartId){
        try {
            BigDecimal totalPrice = cartservice.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("total Price :",totalPrice));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Exception ",e.getMessage()));
        }
    }

}
