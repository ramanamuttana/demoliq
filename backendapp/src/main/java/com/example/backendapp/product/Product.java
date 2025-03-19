package com.example.backendapp.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private Long productId;
    private ProductMaintenance productMaintenance;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductMaintenance {
        private String productCode;
        private String productMaterialType;
        private String materialDescription;
        private String barcode;
        private String length;
        private String width;
        private String height;
        private String dimension;
        private String weight;
        private String supplierName;
        private Double price;
        private String currency;
        private String velocity;
        private String insertedUser;
        private Timestamp insertedTimestamp;
        private String lastModifiedUser;
        private Timestamp lastModifiedtimestamp;
        private Boolean productActiveStatus;
    }
}
