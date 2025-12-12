package com.ecommerce.shop_service.service.impl;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.*;
import com.ecommerce.shop_service.domain.enumeration.OrderStatus;
import com.ecommerce.shop_service.dto.order.OrderItemRequest;
import com.ecommerce.shop_service.dto.order.OrderRequest;
import com.ecommerce.shop_service.dto.order.OrderResponse;
import com.ecommerce.shop_service.repository.OrderRepository;
import com.ecommerce.shop_service.repository.ProductRepository;
import com.ecommerce.shop_service.security.SecurityUtils;
import com.ecommerce.shop_service.service.OrderService;
import com.ecommerce.shop_service.service.mapper.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        // 1. Lấy User ID từ Token (Security Context)
        String currentUserId = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        // 2. Khởi tạo Order
        Order order = new Order();
        order.setUserId(currentUserId);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(request.getShippingAddress());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Duyệt qua từng sản phẩm khách mua
        for (OrderItemRequest itemRequest : request.getItems()) {
            // Tìm sản phẩm trong DB (để lấy giá chuẩn)
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại ID: " + itemRequest.getProductId()));

            // Check tồn kho (Optional: nên trừ tồn kho ở đây)
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ số lượng tồn kho");
            }
            // Trừ tồn kho
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            // Tạo OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // Link ngược lại Order
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice()); // 🔥 LẤY GIÁ TỪ DB (Snapshot)

            orderItems.add(orderItem);

            // Cộng dồn tổng tiền: Price * Quantity
            BigDecimal lineAmount = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(lineAmount);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        // 4. Lưu xuống DB (Cascade sẽ tự lưu OrderItem)
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng: " + id));

        // (Optional) Check quyền: User chỉ xem được đơn của mình
        // String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        // if (!order.getUserId().equals(currentUserId)) throw ...

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<OrderResponse> getAll(Specification<Order> spec, Pageable pageable) {
        // Admin mới dùng hàm này để xem tất cả
        Page<Order> pageEntity = orderRepository.findAll(spec, pageable);
        return new Pagination<>(pageEntity.map(orderMapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<OrderResponse> getMyOrders(Pageable pageable) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        // Dùng Specification để filter theo userId
        Specification<Order> spec = (root, query, cb) -> cb.equal(root.get("userId"), currentUserId);

        Page<Order> pageEntity = orderRepository.findAll(spec, pageable);
        return new Pagination<>(pageEntity.map(orderMapper::toResponse));
    }
}