package com.ecommerce.shop_service.repository;

import com.ecommerce.shop_service.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // Tìm đơn hàng của user cụ thể (Dùng cho lịch sử mua hàng)
    List<Order> findByUserId(String userId);
}