package com.ecommerce.shop_service.service;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.dto.orderItem.OrderItemResponse;
import com.ecommerce.shop_service.domain.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface OrderItemService {
    OrderItemResponse getById(Long id);
    Pagination<OrderItemResponse> getAll(Specification<OrderItem> spec, Pageable pageable);
}