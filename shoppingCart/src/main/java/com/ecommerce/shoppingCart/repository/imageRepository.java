package com.ecommerce.shoppingCart.repository;

import com.ecommerce.shoppingCart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface imageRepository extends JpaRepository<Image , Long> {
    public List<Image> findByProductId(long productId);
}
