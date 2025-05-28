package com.ecommerce.shoppingCart.service.cart;

import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Cart;
import com.ecommerce.shoppingCart.model.CartItem;
import com.ecommerce.shoppingCart.model.Product;
import com.ecommerce.shoppingCart.repository.cartItemRepository;
import com.ecommerce.shoppingCart.repository.cartRepository;
import com.ecommerce.shoppingCart.service.product.iProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class cartItemService implements ICartItemService {

    private final ICartService cartservice;
    private final iProductService productService;
    private final cartItemRepository cartitemRepo;
    private final cartRepository cartRepo;
    public cartItemService(cartService cartservice, iProductService productService, cartItemRepository cartitemRepo, cartRepository cartRepo) {
        this.cartservice = cartservice;
        this.productService = productService;
        this.cartitemRepo = cartitemRepo;
        this.cartRepo = cartRepo;
    }


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1.get the cart
        Cart cart = cartservice.getCart(cartId);

        //2.get the product
        Product p = productService.getProductById(productId);

        //3.check if the product is already exist in the cart
        CartItem cartItem = cart.
                 getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());


        //5.if no , initiate a new cart item entry
        if(cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(p);
            cartItem.setUnitPrice(p.getPrice());
        }else { //4.if yes , increase the quantity with the requested quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();

        cart.addItem(cartItem);
        cartitemRepo.save(cartItem);
        cartRepo.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        //1.get cart
        Cart cart = cartservice.getCart(cartId);

        //get the product if exist decrease quantity of product from cart
        CartItem cartItem = getCartItem(cartId, productId);

        cart.removeItem(cartItem);
        cartRepo.save(cart);

        if(cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity()-1);
        }

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        //1.get cart
        Cart cart = cartservice.getCart(cartId);

        //2.get the product if exist set quantity of product
          cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {item.setQuantity(quantity);
                                    item.setUnitPrice(item.getProduct().getPrice());
                                     item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepo.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartservice.getCart(cartId);

     return cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

    }
}
