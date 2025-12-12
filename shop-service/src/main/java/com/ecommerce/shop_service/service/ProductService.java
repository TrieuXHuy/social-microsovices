package com.ecommerce.shop_service.service;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Product;
import com.ecommerce.shop_service.dto.product.ProductRequest;
import com.ecommerce.shop_service.dto.product.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    Pagination<ProductResponse> getAll(Specification<Product> spec, Pageable pageable);
}