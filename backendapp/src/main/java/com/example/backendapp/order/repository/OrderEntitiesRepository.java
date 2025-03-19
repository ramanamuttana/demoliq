package com.example.backendapp.order.repository;

import com.example.backendapp.order.model.OrderDTO;
import com.example.backendapp.order.model.OrderEntities;
import com.example.backendapp.order.request.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderEntitiesRepository extends JpaRepository<OrderEntities, Long> {

    @Transactional
    @Modifying
    @Query("update OrderEntities o set o.quantity = ?1 where o.skuId = ?2") OrderDTO updateOrder(Long id,OrderRequest orderRequest);

}
