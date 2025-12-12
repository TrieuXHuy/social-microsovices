package com.ecommerce.shop_service.service.mapper;

import com.ecommerce.shop_service.domain.Order;
import com.ecommerce.shop_service.domain.OrderItem;
import com.ecommerce.shop_service.dto.order.OrderItemResponse;
import com.ecommerce.shop_service.dto.order.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    OrderResponse toResponse(Order entity);

    // Map chi tiết đơn hàng
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponse toItemResponse(OrderItem item);
}