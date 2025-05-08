package com.ecommerce.shoppingCart.service.product;

import com.ecommerce.shoppingCart.model.Product;
import com.ecommerce.shoppingCart.request.AddProductRequest;
import com.ecommerce.shoppingCart.request.updateProductRequest;

import java.util.List;

public interface iProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    Product updateProduct(updateProductRequest request, Long id);
    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
