package com.ecommerce.shop_service.web.res;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Category;
import com.ecommerce.shop_service.dto.category.CategoryRequest;
import com.ecommerce.shop_service.dto.category.CategoryResponse;
import com.ecommerce.shop_service.service.CategoryService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse result = categoryService.create(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    /**
     * API này hỗ trợ lọc nâng cao bằng Spring Filter
     * Ví dụ: GET /api/v1/categories?filter=name:'Laptop' and description ~~ '%gaming%'
     */
    @GetMapping
    public ResponseEntity<Pagination<CategoryResponse>> getAllCategories(
            @Filter Specification<Category> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(categoryService.getAll(spec, pageable));
    }
}