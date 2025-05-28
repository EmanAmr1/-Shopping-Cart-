package com.ecommerce.shoppingCart.service.cart;

import com.ecommerce.shoppingCart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
