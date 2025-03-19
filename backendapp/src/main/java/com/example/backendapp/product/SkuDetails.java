package com.example.backendapp.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SkuDetails {
    private Long productId;
    private String productCode;
    private String productDescription;
}
