package com.ecommerce.shoppingCart.repository;

import com.ecommerce.shoppingCart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
