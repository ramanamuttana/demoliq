package com.example.backendapp.order;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.example.backendapp.order.model.*;
import com.example.backendapp.order.repository.OrderRepository;
import com.example.backendapp.order.request.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonObjectfinal = objectMapper.createObjectNode();

    public List<Order> getAllOrders() {
        List<OrderDTO> orders = orderRepository.findAllActiveOrders();
        return orders.stream()
                .map(this::mapToOrder)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        var order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("The Order with %s, does not exist", orderId)
            );
        }
        return mapToOrder(order.get());
    }


    public Long createOrder(OrderRequest orderRequest) {
        OrderDTO orderDTO = null;

            var requestedUser = orderRequest.getUserName();
            var priority=orderRequest.getOrder().getOrderMaintenance().getPriority();
            if(priority== PRIORITY.URGENT){
             orderDTO = mapToOrderDTO(orderRequest.getOrder().getOrderMaintenance(), null, "create", requestedUser,LocalDate.now().plusDays(2), null, null);
            }else {
             orderDTO= mapToOrderDTO(orderRequest.getOrder().getOrderMaintenance(), null, "create", requestedUser,LocalDate.now().plusDays(10), null, null);
            }
            var savedOrderEntity = orderRepository.save(orderDTO);
            return savedOrderEntity.getId();

    }

    public OrderDTO updateOrder(Long orderId, OrderRequest orderRequest) {
        var requestedUser = orderRequest.getUserName();
        var existingOrder = orderRepository.findById(orderId);
        OrderDTO updatedOrderDTO=null;

        if (existingOrder.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The Order does not exist"
            );
        }else {
        var priority=orderRequest.getOrder().getOrderMaintenance().getPriority();

             updatedOrderDTO = mapToOrderDTO(orderRequest.getOrder().getOrderMaintenance(), orderId, "update" , requestedUser,null, existingOrder.get().getInsertedUser(), existingOrder.get().getInsertedTimestamp());
        return  orderRepository.save(updatedOrderDTO);
        }
    }

    public Boolean deleteOrder(Long orderId) {
        var existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The Order does not exist"
            );
        }

        var orderToDelete = existingOrder.get();
        orderToDelete.setOrderActiveStatus(false);

        orderRepository.save(orderToDelete);

        return true;
    }

    private Order mapToOrder(OrderDTO orderDTO) {
        return new Order(
                orderDTO.getId(),
                new Order.OrderMaintenance(
                        orderDTO.getOrderType(),
                        orderDTO.getPriority(),
                        orderDTO.getSource(),
                       orderDTO.getTotalQuantity(),
                        orderDTO.getTotalSkus(),
                        orderDTO.getCustomer(),
                        orderDTO.getOrderStatus(),
                        orderDTO.getDeliveryInstructions(),
                        orderDTO.getExpectedDeliveryDate(),
                        orderDTO.getDispatchedTimeFromDC(),
                        orderDTO.getInsertedUser(),
                        orderDTO.getInsertedTimestamp(),
                        orderDTO.getLastModifiedUser(),
                        orderDTO.getLastModifiedtimestamp(),
                        orderDTO.getOrderEntities(),
                        true
                )
        );
    }

    private OrderDTO mapToOrderDTO(
            Order.OrderMaintenance orderMaintenance,
            Long orderId,
            String operation,
            String userName,
            LocalDate localdate,
            String insertedUser,
            Timestamp insertedTimestamp
    ) {
        var totalQuantity = orderMaintenance.getOrderEntities().stream()
                .map(orderEntity -> Long.parseLong(orderEntity.getQuantity()))
                .reduce(0L, Long::sum);


        var totalSkus = orderMaintenance.getOrderEntities().size();
        if (Objects.equals(operation, "create")) {
            return new OrderDTO(
                    null, // this should be auto-generated for create
                    orderMaintenance.getOrderType(),
                    orderMaintenance.getPriority(),
                    orderMaintenance.getSource(),
                    totalQuantity,
                    (long)totalSkus,
                    orderMaintenance.getCustomer(),
                    orderMaintenance.getOrderStatus(),
                    orderMaintenance.getDeliveryInstructions(),
                    localdate,
                    orderMaintenance.getDispatchedTimeFromDC(),
                    userName,
                    Timestamp.from(Instant.now()),
                    orderMaintenance.getLastModifiedUser(),
                    orderMaintenance.getLastModifiedtimestamp(),
                    (List<OrderEntities>) orderMaintenance.getOrderEntities(),
                    true
            );
        } else if (Objects.equals(operation, "update")) {
            return new OrderDTO(
                    orderId, // this should be auto-generated for create
                    orderMaintenance.getOrderType(),
                    orderMaintenance.getPriority(),
                    orderMaintenance.getSource(),
                    totalQuantity,
                    (long)totalSkus,
                    orderMaintenance.getCustomer(),
                    orderMaintenance.getOrderStatus(),
                    orderMaintenance.getDeliveryInstructions(),
                    orderMaintenance.getExpectedDeliveryDate(),
                    orderMaintenance.getDispatchedTimeFromDC(),
                    insertedUser,
                    insertedTimestamp,
                    userName,
                    Timestamp.from(Instant.now()),
                    (List<OrderEntities>) orderMaintenance.getOrderEntities(),
                    true
            );
        }
        return new OrderDTO(
                orderId,
                orderMaintenance.getOrderType(),
                orderMaintenance.getPriority(),
                orderMaintenance.getSource(),
                totalQuantity,
                (long)totalSkus,
                orderMaintenance.getCustomer(),
                orderMaintenance.getOrderStatus(),
                orderMaintenance.getDeliveryInstructions(),
                orderMaintenance.getExpectedDeliveryDate(),
                orderMaintenance.getDispatchedTimeFromDC(),
                orderMaintenance.getInsertedUser(),
                orderMaintenance.getInsertedTimestamp(),
                orderMaintenance.getLastModifiedUser(),
                orderMaintenance.getLastModifiedtimestamp(),
                (List<OrderEntities>) orderMaintenance.getOrderEntities(),
                true
        );
    }

    // grpah source
    public String countOrdersBySource() throws JsonProcessingException {

        ArrayNode jsonArray = objectMapper.createArrayNode();
        for (Object[] keyValue : orderRepository.countOrdersBySource()) {

            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put(keyValue[0].toString(), keyValue[1].toString());
            jsonArray.add(jsonObject);
        }

           // String jsonString = objectMapper.writeValueAsString(jsonArray);
            String[] keysToCheck = {"Website", "Google", "Direct","Argus","E-mag","Key-account","Bucharest"};

        try {
            ObjectMapper mapper = new ObjectMapper();
          //  JsonNode jsonArra = mapper.readTree(jsonString);
            for (String key : keysToCheck) {
            for (JsonNode jsonNode : jsonArray) {
                    if (!jsonNode.has(key)) {
                        jsonObjectfinal.put(key, 0);
                    }else {
                        jsonObjectfinal.put(key,jsonNode.get(key).asText());
                    }
                }
            }

            return jsonObjectfinal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // JSON parsing or other error
        }

    }

    public  List<PriorityCountDTO>  countPriorityInLastTenDays() {
        Timestamp endDate = Timestamp.from(Instant.now());
        Timestamp startDate = Timestamp.from(Instant.now().minus(10, ChronoUnit.DAYS));

       return orderRepository.findPriorityCountsInDateRange(startDate, endDate);
    }

    public   List<OrderCountDTO> getOrderCountForLastTwelveMonths() throws JsonProcessingException {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -12);
//        Date startDate = (Date) calendar.getTime(); // ERROR
//
//        return orderRepository.findOrderCountForLastTwelveMonths(startDate);
//
        return null;
    }
}
