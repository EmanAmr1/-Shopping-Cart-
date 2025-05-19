package com.ecommerce.shoppingCart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private Set<CartItem> cartItems;

    private BigDecimal totalPrice;

    //Default value
    private BigDecimal totalAmount=BigDecimal.ZERO;


}
