package com.ecommerce.shop_service.service.impl;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.OrderItem;
import com.ecommerce.shop_service.dto.orderItem.OrderItemResponse;
import com.ecommerce.shop_service.repository.OrderItemRepository;
import com.ecommerce.shop_service.service.OrderItemService;
import com.ecommerce.shop_service.service.mapper.OrderItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // Read-only cho toàn bộ class này
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItemResponse getById(Long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng ID: " + id));
        return orderItemMapper.toResponse(item);
    }

    @Override
    public Pagination<OrderItemResponse> getAll(Specification<OrderItem> spec, Pageable pageable) {
        Page<OrderItem> pageEntity = orderItemRepository.findAll(spec, pageable);
        return new Pagination<>(pageEntity.map(orderItemMapper::toResponse));
    }
}