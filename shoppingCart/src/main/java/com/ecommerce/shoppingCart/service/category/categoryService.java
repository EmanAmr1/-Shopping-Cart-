package com.ecommerce.shoppingCart.service.category;

import com.ecommerce.shoppingCart.exception.AlreadyExistsException;
import com.ecommerce.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerce.shoppingCart.model.Category;
import com.ecommerce.shoppingCart.repository.categoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class categoryService implements iCategoryService {

    private final categoryRepository categoryRepo;

    public categoryService(categoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found !"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepo.existsByName(c.getName()))
                .map(categoryRepo::save)
                .orElseThrow(()-> new AlreadyExistsException( category.getName() +" Already Exists !"));
    }



    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepo.save(oldCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found !"));
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,
                () -> {
                    throw new ResourceNotFoundException("Category Not Found !");
                });
    }
}
