package com.example.backendapp.order.repository;

import com.example.backendapp.order.model.Customer;
import com.example.backendapp.order.model.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
