package com.ecommerce.shoppingCart.request;

import com.ecommerce.shoppingCart.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class updateProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
