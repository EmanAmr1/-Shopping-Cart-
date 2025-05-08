package com.ecommerce.shoppingCart.exception;

public class ProductNotFoundException extends RuntimeException{

   public ProductNotFoundException(String message){
        super(message);
    }
}
