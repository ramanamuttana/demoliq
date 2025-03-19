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
@Table(name="customer_details")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customerName", nullable = false)
    private String customerName;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "emailId", nullable = false)
    private String emailId;

    // uni directional parent to child
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="fk_shipping-address_id")
    private ShippingAddress shippingAddress;

    // uni directional parent to child
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="fk_billing-address_id")
    private BillingAddress billingAddress;

}
