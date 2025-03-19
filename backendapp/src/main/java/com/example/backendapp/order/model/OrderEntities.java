package com.example.backendapp.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="order-entities_details")
public class OrderEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderEntities_id")
    private Long id;

    @Column(name = "skuID", nullable = false)
    private String skuId;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "discount")
    private String discount;

}
