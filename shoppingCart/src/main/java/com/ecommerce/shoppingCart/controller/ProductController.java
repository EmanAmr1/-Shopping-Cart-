package com.ecommerce.shoppingCart.controller;

import com.ecommerce.shoppingCart.dto.ProductDto;
import com.ecommerce.shoppingCart.model.Product;
import com.ecommerce.shoppingCart.request.AddProductRequest;
import com.ecommerce.shoppingCart.request.updateProductRequest;
import com.ecommerce.shoppingCart.response.ApiResponse;
import com.ecommerce.shoppingCart.service.product.iProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final iProductService productService;

    public ProductController(iProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewProduct(@RequestBody AddProductRequest addProductRequest) {
        try {
            Product p = productService.addProduct(addProductRequest);
            ProductDto productDto =productService.convertToDto(p);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while adding product", e.getMessage()));
        }
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long id) {
        try {
            Product p = productService.getProductById(id);
            ProductDto productDto =productService.convertToDto(p);
            return ResponseEntity.ok().body(new ApiResponse("Product found", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody updateProductRequest request, @PathVariable Long id) {
        try {
            Product p = productService.getProductById(id);
            if (p != null) {
                productService.updateProduct(request, id);
                ProductDto productDto =productService.convertToDto(p);
                return ResponseEntity.ok().body(new ApiResponse("Product updated",productDto));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error while adding product", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            Product P = productService.getProductById(id);
            if (P != null) {
                productService.deleteProductById(id);
                ProductDto productDto =productService.convertToDto(P);
                return ResponseEntity.ok().body(new ApiResponse("Product deleted", productDto));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Error while deleting product", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("delete failed!", INTERNAL_SERVER_ERROR));
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getProducts() {
        try {
            List<Product> productList = productService.getAllProducts();
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( "No Products found", null));
            }
            List<ProductDto> productDtoList = productService.getConvertedProducts(productList);
            return ResponseEntity.ok().body(new ApiResponse("Products ", productDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse( e.getMessage(), null));
        }
    }


    @GetMapping("/product/By_Category/{categoryId}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String categoryId) {
        try {
            List<Product> productList = productService.getProductsByCategory(categoryId);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( "No Products found", null));
            }
            List<ProductDto> productDtoList = productService.getConvertedProducts(productList);
            return ResponseEntity.ok().body(new ApiResponse("Products ", productDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse( e.getMessage(), null));
        }
    }

    @GetMapping("/product/By_Brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        try {
            List<Product> productList = productService.getProductsByBrand(brand);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found", null));
            }
            List<ProductDto> productDtoList = productService.getConvertedProducts(productList);
            return ResponseEntity.ok().body(new ApiResponse("Products ", productDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/By_Brand_And_Name/{brand}/{name}")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        try {
            List<Product> productList = productService.getProductsByBrandAndName(brand, name);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Products found", null));
            }
            List<ProductDto> productDtoList = productService.getConvertedProducts(productList);
            return ResponseEntity.ok().body(new ApiResponse("Products ", productDtoList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/By_Brand_And_Name/{brand}/{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok().body(new ApiResponse("Product count ", count));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Error while count Products By brand And Name ", e.getMessage()));
        }
    }

}
