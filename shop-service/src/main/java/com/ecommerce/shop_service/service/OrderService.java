package com.ecommerce.shop_service.service;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.dto.order.OrderRequest;
import com.ecommerce.shop_service.dto.order.OrderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.ecommerce.shop_service.domain.Order;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    OrderResponse getById(Long id);
    Pagination<OrderResponse> getAll(Specification<Order> spec, Pageable pageable);

    // Chỉ cần hàm lấy đơn của user hiện tại
    Pagination<OrderResponse> getMyOrders(Pageable pageable);
}