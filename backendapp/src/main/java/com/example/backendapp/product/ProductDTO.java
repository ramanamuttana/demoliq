package com.example.backendapp.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ProductData")
public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "productCode", nullable = false, unique = true)
    private String productCode;

    @Column(name = "productMaterialType")
    private String productMaterialType;

    @Column(name = "materialDescription", nullable = false)
    private String materialDescription;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "length")
    private String length;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "supplierName")
    private String supplierName;

    @Column(name = "price")
    private Double price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "velocity")
    private String velocity;

    @Column(name="insertedUser")
    private String insertedUser;

    @Column(name = "insertedTimestamp")
    private Timestamp insertedTimestamp;

    @Column(name = "lastModifiedUser")
    private String lastModifiedUser;

    @Column(name = "lastModifiedTimestamp")
    private Timestamp lastModifiedtimestamp;

    @Column(name = "productActiveStatus", columnDefinition = "boolean default true")
    private Boolean productActiveStatus;
}
