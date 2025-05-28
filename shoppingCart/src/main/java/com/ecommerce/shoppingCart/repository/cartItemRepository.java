package com.ecommerce.shoppingCart.repository;

import com.ecommerce.shoppingCart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId (Long cartId);
}
