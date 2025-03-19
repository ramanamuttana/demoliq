package com.example.backendapp.order.model;

import com.example.backendapp.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="order_details")
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_id")
    private Long id;

    @Column(name = "orderType", nullable = false)
    private String orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private PRIORITY priority;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "totalQuantity", nullable = false)
    private Long totalQuantity;

    @Column(name = "totalSkus", nullable = false)
    private Long totalSkus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_customer_id")
    private Customer customer;

    @Column(name = "orderStatus")
    private String orderStatus;

    @Column(name = "deliveryInstructions")
    private String deliveryInstructions;

    @Column(name = "expectedDeliveryDate")
    private LocalDate expectedDeliveryDate;

    @Column(name = "dispatchedTimeFromDC")
    private LocalDate dispatchedTimeFromDC;

    @Column(name = "insertedUser")
    private String insertedUser;

    @Column(name = "insertedTimestamp")
    private Timestamp insertedTimestamp;

    @Column(name = "lastModifiedUser")
    private String lastModifiedUser;

    @Column(name = "lastModifiedTimestamp")
    private Timestamp lastModifiedtimestamp;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_order_id",referencedColumnName = "order_id")
    private List<OrderEntities> orderEntities;

    @Column(name = "orderActiveStatus", columnDefinition = "boolean default true")
    private Boolean orderActiveStatus;

}

