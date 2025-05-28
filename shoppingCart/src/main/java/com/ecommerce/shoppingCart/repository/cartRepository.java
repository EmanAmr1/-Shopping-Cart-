package com.ecommerce.shoppingCart.repository;

import com.ecommerce.shoppingCart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartRepository extends JpaRepository<Cart, Long> {
}
