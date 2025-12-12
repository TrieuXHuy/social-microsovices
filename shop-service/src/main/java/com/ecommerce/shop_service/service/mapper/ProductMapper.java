package com.ecommerce.shop_service.service.mapper;

import com.ecommerce.shop_service.domain.Product;
import com.ecommerce.shop_service.dto.product.ProductRequest;
import com.ecommerce.shop_service.dto.product.ProductResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    // Entity -> Response
    ProductResponse toResponse(Product entity);

    // Request -> Entity (Bỏ qua category vì sẽ set tay trong Service)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequest request);

    // Update Entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true) // Sẽ xử lý category riêng logic update
    void updateEntityFromRequest(ProductRequest request, @MappingTarget Product entity);
}