package com.example.backendapp.order;

import com.example.backendapp.order.model.Order;
import com.example.backendapp.order.model.OrderCountDTO;
import com.example.backendapp.order.model.OrderDTO;
import com.example.backendapp.order.model.PriorityCountDTO;
import com.example.backendapp.order.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/create")
    public Long createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @Transactional
    @PutMapping("/update/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(id, orderRequest);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/source")
    public String getSource() throws JsonProcessingException {
      return  orderService.countOrdersBySource();
    }

    @GetMapping("/priority")
    public ResponseEntity<List<PriorityCountDTO>> getPriority(){
        return  new ResponseEntity<>(orderService.countPriorityInLastTenDays(), HttpStatus.OK);
    }

    @GetMapping("/ordersPerMonth")
    public List<OrderCountDTO> getOrdersPerYear() throws JsonProcessingException {
        return orderService.getOrderCountForLastTwelveMonths();
    }

    @GetMapping("/status")
    public List<Object[]> getStatus(){
        return  null;
    }

    @GetMapping("/usersHighestOrders")
    public List<Object[]> getUsersHighestOrders(){
        return  null;
    }
}
