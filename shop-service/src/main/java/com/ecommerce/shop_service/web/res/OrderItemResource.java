package com.ecommerce.shop_service.web.res;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.OrderItem;
import com.ecommerce.shop_service.dto.orderItem.OrderItemResponse;
import com.ecommerce.shop_service.service.OrderItemService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemResource {

    private final OrderItemService orderItemService;

    public OrderItemResource(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getById(id));
    }

    /**
     * API Thống kê & Tìm kiếm chi tiết đơn hàng
     * Ví dụ sử dụng Filter:
     * 1. Tìm tất cả lượt mua iPhone 15:
     * GET /api/v1/order-items?filter=product.name ~~ '%iPhone 15%'
     * 2. Tìm tất cả item thuộc đơn hàng số 10:
     * GET /api/v1/order-items?filter=order.id : 10
     * 3. Tìm các item có giá bán > 10 triệu:
     * GET /api/v1/order-items?filter=price > 10000000
     */
    @GetMapping
    public ResponseEntity<Pagination<OrderItemResponse>> getAllOrderItems(
            @Filter Specification<OrderItem> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(orderItemService.getAll(spec, pageable));
    }
}