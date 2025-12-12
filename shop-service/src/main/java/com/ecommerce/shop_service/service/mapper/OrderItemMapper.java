package com.ecommerce.shop_service.service.mapper;

import com.ecommerce.shop_service.domain.OrderItem;
import com.ecommerce.shop_service.dto.orderItem.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImageUrl", source = "product.imageUrl")
    @Mapping(target = "totalPrice", source = ".", qualifiedByName = "calculateTotalPrice") // Tự tính thành tiền
    OrderItemResponse toResponse(OrderItem entity);

    @Named("calculateTotalPrice")
    default BigDecimal calculateTotalPrice(OrderItem item) {
        if (item.getPrice() == null || item.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        return item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }
}
