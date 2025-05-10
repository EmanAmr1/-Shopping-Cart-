package com.ecommerce.shoppingCart.repository;

import com.ecommerce.shoppingCart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface imageRepository extends JpaRepository<Image , Long> {
}
