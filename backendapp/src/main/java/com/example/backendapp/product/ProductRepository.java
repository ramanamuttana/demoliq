package com.example.backendapp.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository  extends JpaRepository<ProductDTO, Long> {
    @Query(
            value = "SELECT * FROM product_data p WHERE p.product_active_status = true",
            nativeQuery = true
    )
    List<ProductDTO> findAllActiveProducts();
}
