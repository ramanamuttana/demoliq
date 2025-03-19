package com.example.backendapp.order.repository;

import com.example.backendapp.order.model.OrderDTO;
import com.example.backendapp.order.model.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {


}
