package com.example.backendapp.order.request;

import com.example.backendapp.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private String userName;
    private Order order;
}
