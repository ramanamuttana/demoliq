package com.example.backendapp.order.model;

import com.example.backendapp.order.model.Customer;
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
@Table(name="shipping_details")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shipping-address_id")
    private Long id;

    @Column(name = "streetName", nullable = false)
    private String streetName;

    @Column(name = "houseNumber", nullable = false)
    private String houseNumber;

    @Column(name = "county", nullable = false)
    private String county;

    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "postalCode", nullable = true)
    private Long postalCode;
}
