package com.ecommerce.shop_service.web.res;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Order;
import com.ecommerce.shop_service.dto.order.OrderRequest;
import com.ecommerce.shop_service.dto.order.OrderResponse;
import com.ecommerce.shop_service.service.OrderService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    // Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        return new ResponseEntity<>(orderService.create(request), HttpStatus.CREATED);
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    // Xem lịch sử đơn hàng của tôi (User đang login)
    @GetMapping("/my-orders")
    public ResponseEntity<Pagination<OrderResponse>> getMyOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getMyOrders(pageable));
    }

    // Admin xem tất cả & Lọc (Ví dụ: Lọc đơn > 1 triệu & Status = PAID)
    // GET /api/v1/orders?filter=totalAmount > 1000000 and status : 'PAID'
    @GetMapping
    public ResponseEntity<Pagination<OrderResponse>> getAllOrders(
            @Filter Specification<Order> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getAll(spec, pageable));
    }
}