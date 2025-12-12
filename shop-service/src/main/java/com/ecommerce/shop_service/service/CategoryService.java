package com.ecommerce.shop_service.service;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Category;
import com.ecommerce.shop_service.dto.category.CategoryRequest;
import com.ecommerce.shop_service.dto.category.CategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
    CategoryResponse getById(Long id);

    // Hàm này nhận Specification để filter
    Pagination<CategoryResponse> getAll(Specification<Category> spec, Pageable pageable);
}