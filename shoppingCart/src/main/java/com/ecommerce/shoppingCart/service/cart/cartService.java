package com.ecommerce.shoppingCart.service.cart;

import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Cart;
import com.ecommerce.shoppingCart.repository.cartItemRepository;
import com.ecommerce.shoppingCart.repository.cartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class cartService implements ICartService{

    private final cartRepository cartRepo;
    private final cartItemRepository cartItemRepo;
    public cartService(cartRepository cartRepo, cartItemRepository cartItemRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepo.findById(id).orElseThrow( ()-> new  ResourceNotFoundException("Cart Not Found"));

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        cartItemRepo.deleteAllByCartId(id); //delete all items in cart
        Cart cart = getCart(id);      //get cart by id
        cart.getCartItems().clear(); //clear items from cart
        cartRepo.deleteById(id);    //delete cart
    }

    /* @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getCartItems().stream().map(CartItem::getTotalPrice) //retrieve every item total price
                .reduce(BigDecimal.ZERO, BigDecimal::add);  //.reduce(BigDecimal.ZERO, (a, b) -> a.add(b)) start from 0
    }*/


    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }








}
