package com.example.backendapp.product.request;

import com.example.backendapp.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    private String userName;
    private Product product;
}
