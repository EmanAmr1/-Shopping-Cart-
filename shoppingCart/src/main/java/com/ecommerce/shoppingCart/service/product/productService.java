package com.ecommerce.shoppingCart.service.product;

import com.ecommerce.shoppingCart.dto.CategoryDto;
import com.ecommerce.shoppingCart.dto.ImageDto;
import com.ecommerce.shoppingCart.dto.ProductDto;
import com.ecommerce.shoppingCart.model.Category;
import com.ecommerce.shoppingCart.model.Image;
import com.ecommerce.shoppingCart.model.Product;
import com.ecommerce.shoppingCart.repository.categoryRepository;
import com.ecommerce.shoppingCart.repository.imageRepository;
import com.ecommerce.shoppingCart.repository.productRepository;
import com.ecommerce.shoppingCart.exception.ProductNotFoundException;
import com.ecommerce.shoppingCart.request.AddProductRequest;
import com.ecommerce.shoppingCart.request.updateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService implements iProductService {

    private final ModelMapper modelMapper;


    private final productRepository productRepo;
    private final categoryRepository categoryRepo;
    private final imageRepository imageRepo;
    public productService(ModelMapper modelMapper, productRepository productRepo, categoryRepository categoryRepo, imageRepository imageRepo) {
        this.modelMapper = modelMapper;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.imageRepo = imageRepo;
    }

     //Add new Product
    @Override
    public Product addProduct(AddProductRequest request) {
        //check if category not exist add it then save the product
        Category category = categoryRepo.findByName(request.getCategory().getName());
        if (category == null) {
            category = new Category(request.getCategory().getName());
            categoryRepo.save(category);
        }

        return productRepo.save(createProduct(request, category));
    }

    public Product createProduct(AddProductRequest request, Category category) {
        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        return product;
    }




    //getProductById
    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("product not found !"));

    }


    //updateProduct

//    @Override
//    public Product updateProduct(updateProductRequest request, Long id) {
//        Product product = productRepo.findById(id).orElseThrow(
//                () -> new ProductNotFoundException("product not found !")
//        );
//
//        return  productRepo.save(updateExistingProduct(product,request));
//    }

    @Override
    public Product updateProduct(updateProductRequest request, Long id) {
        return productRepo.findById(id)
                .map(existingPro -> updateExistingProduct(existingPro, request))
                .map(productRepo::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
    }


    public Product updateExistingProduct(Product existingProduct, updateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;

    }



    //deleteProductById
    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id)
                .ifPresentOrElse(productRepo::delete,
                        () -> {
                            throw new ProductNotFoundException("product not found !");
                        }
                );
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepo.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndName(brand, name);
    }


    @Override
    public ProductDto convertToDto(Product product) {
        //convert Product To ProductDto
        ProductDto productDto = modelMapper.map(product, ProductDto.class);

        //get images of product
        List<Image> imageList= imageRepo.findByProductId(product.getId());

        //convert Image To ImageDto
        List<ImageDto> imageDtos = imageList
                .stream()
                .map( image -> modelMapper.map(image,ImageDto.class))
                .toList();

        productDto.setImageList(imageDtos);

        CategoryDto categoryDto= modelMapper.map(product.getCategory(), CategoryDto.class);
        productDto.setCategoryDto(categoryDto.getName());

        return productDto;
    }

    @Override
    public List<ProductDto> getConvertedProducts (List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }


}
