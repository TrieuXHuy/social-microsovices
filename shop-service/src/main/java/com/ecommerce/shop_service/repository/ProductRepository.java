package com.ecommerce.shop_service.repository;

import com.ecommerce.shop_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Check trùng tên sản phẩm
    boolean existsByName(String name);
}