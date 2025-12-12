package com.ecommerce.shop_service.service.impl;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Category;
import com.ecommerce.shop_service.dto.category.CategoryRequest;
import com.ecommerce.shop_service.dto.category.CategoryResponse;
import com.ecommerce.shop_service.repository.CategoryRepository;
import com.ecommerce.shop_service.service.CategoryService;
import com.ecommerce.shop_service.service.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tên danh mục đã tồn tại: " + request.getName());
        }
        Category category = categoryMapper.toEntity(request);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + id));

        // Nếu đổi tên thì phải check trùng
        if (request.getName() != null
                && !request.getName().equals(category.getName())
                && categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tên danh mục đã được sử dụng");
        }

        categoryMapper.updateEntityFromRequest(request, category);
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy danh mục để xóa");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<CategoryResponse> getAll(Specification<Category> spec, Pageable pageable) {
        // 1. Tìm Page<Entity> từ DB dựa trên Filter Spec
        Page<Category> pageEntity = categoryRepository.findAll(spec, pageable);

        // 2. Convert Page<Entity> -> Page<ResponseDTO>
        Page<CategoryResponse> pageDto = pageEntity.map(categoryMapper::toResponse);

        // 3. Wrap vào class Pagination dùng chung
        return new Pagination<>(pageDto);
    }
}