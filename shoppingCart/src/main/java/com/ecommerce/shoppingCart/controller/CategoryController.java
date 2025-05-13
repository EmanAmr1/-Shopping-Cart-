package com.ecommerce.shoppingCart.controller;

import com.ecommerce.shoppingCart.model.Category;
import com.ecommerce.shoppingCart.response.ApiResponse;
import com.ecommerce.shoppingCart.service.category.iCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final iCategoryService categoryService;
    public CategoryController(iCategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable Long id) {
        try {
            Category c = categoryService.getCategoryById(id);
            return ResponseEntity.ok().body(new ApiResponse("Category found", c));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse( e.getMessage() ,null));
        }
    }

    @GetMapping("/By_Name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category c = categoryService.getCategoryByName(name);
            return ResponseEntity.ok().body(new ApiResponse("Category found", c));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while getting category", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> saveCategory(@RequestBody Category category) {
        try {
            Category c = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category saved", c));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while saving category", e.getMessage()));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        try {
            Category c = categoryService.updateCategory(category, id);
            return ResponseEntity.ok().body(new ApiResponse("Category updated", c));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while updating category", e.getMessage()));
        }
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Category List ", categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while getting Category List", e.getMessage()));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            Category c = categoryService.getCategoryById(id);
            if (c != null) {
                categoryService.deleteCategoryById(id);
                return ResponseEntity.ok().body(new ApiResponse("Category deleted", c));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Error while deleting Category", e.getMessage()));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("delete failed!", INTERNAL_SERVER_ERROR));
    }

}
