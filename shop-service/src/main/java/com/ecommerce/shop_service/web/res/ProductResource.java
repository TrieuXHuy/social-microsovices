package com.ecommerce.shop_service.web.res;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Product;
import com.ecommerce.shop_service.dto.product.ProductRequest;
import com.ecommerce.shop_service.dto.product.ProductResponse;
import com.ecommerce.shop_service.service.ProductService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse result = productService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    /**
     * API Lọc nâng cao:
     * 1. Lọc theo tên: ?filter=name ~~ '%MacBook%'
     * 2. Lọc theo giá: ?filter=price > 1000
     * 3. Lọc theo tên danh mục (Join bảng): ?filter=category.name ~~ '%Laptop%'
     */
    @GetMapping
    public ResponseEntity<Pagination<ProductResponse>> getAllProducts(
            @Filter Specification<Product> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(productService.getAll(spec, pageable));
    }
}