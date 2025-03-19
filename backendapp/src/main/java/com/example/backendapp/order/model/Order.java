package com.example.backendapp.order.model;

import com.example.backendapp.product.Product;
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
public class Order {

    private Long orderId;
    private Order.OrderMaintenance orderMaintenance;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OrderMaintenance {
        private String orderType;
        private PRIORITY priority;
        private String source;
        private Long totalQuantity;
        private Long totalSkus;
        private Customer customer;
        private String orderStatus;
        private String deliveryInstructions;
        private LocalDate expectedDeliveryDate;
        private LocalDate dispatchedTimeFromDC;
        private String insertedUser;
        private Timestamp insertedTimestamp;
        private String lastModifiedUser;
        private Timestamp lastModifiedtimestamp;
        private List<OrderEntities> orderEntities;
        private Boolean orderActiveStatus;
    }
}
