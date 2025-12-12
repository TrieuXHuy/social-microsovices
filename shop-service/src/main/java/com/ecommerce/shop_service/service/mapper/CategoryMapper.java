package com.ecommerce.shop_service.service.mapper;

import com.ecommerce.shop_service.domain.Category;
import com.ecommerce.shop_service.dto.category.CategoryRequest;
import com.ecommerce.shop_service.dto.category.CategoryResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // Convert Entity -> Response DTO
    CategoryResponse toResponse(Category entity);

    // Convert Request DTO -> Entity (Create)
    Category toEntity(CategoryRequest request);

    // Update Entity from Request DTO (Update)
    // ignore null values: chỉ update trường nào có dữ liệu
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(CategoryRequest request, @MappingTarget Category entity);
}