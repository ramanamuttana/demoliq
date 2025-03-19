package com.example.backendapp.order.repository;

import com.example.backendapp.order.model.OrderCountDTO;
import com.example.backendapp.order.model.OrderDTO;
import com.example.backendapp.order.model.PriorityCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
    @Query(
            value = "SELECT * FROM order_details o WHERE o.order_active_status = true",
            nativeQuery = true
    )
    List<OrderDTO> findAllActiveOrders();

    @Query(
            value = "SELECT o.source, COUNT(o.source) FROM OrderDTO AS o GROUP BY o.source")
    List<Object[]> countOrdersBySource();


  @Query("SELECT NEW com.example.backendapp.order.model.PriorityCountDTO(o.insertedTimestamp, o.priority, COUNT(o)) " +
          "FROM OrderDTO o " +
          "WHERE o.insertedTimestamp BETWEEN :startDate AND :endDate " +
          "GROUP BY o.insertedTimestamp,o.priority")
    List<PriorityCountDTO> findPriorityCountsInDateRange(
          @Param("startDate") Timestamp startDate,
          @Param("endDate") Timestamp endDate);

    @Query("SELECT DATE_TRUNC('month', o.insertedTimestamp) as month, COUNT(o.id) as orderCount " +
            "FROM OrderDTO o " +
            "WHERE o.insertedTimestamp >= :startDate " +
            "GROUP BY DATE_TRUNC('month', o.insertedTimestamp) " +
            "ORDER BY month")
    List<OrderCountDTO> findOrderCountForLastTwelveMonths(@Param("startDate") Date startDate);
}





