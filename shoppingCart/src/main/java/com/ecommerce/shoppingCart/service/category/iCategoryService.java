package com.ecommerce.shoppingCart.service.category;

import com.ecommerce.shoppingCart.model.Category;

import java.util.List;

public interface iCategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category ,Long id);
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);

}
