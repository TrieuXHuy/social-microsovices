package com.ecommerce.shop_service.service.impl;

import com.ecommerce.common.dto.Pagination;
import com.ecommerce.shop_service.domain.Category;
import com.ecommerce.shop_service.domain.Product;
import com.ecommerce.shop_service.dto.product.ProductRequest;
import com.ecommerce.shop_service.dto.product.ProductResponse;
import com.ecommerce.shop_service.repository.CategoryRepository;
import com.ecommerce.shop_service.repository.ProductRepository;
import com.ecommerce.shop_service.service.ProductService;
import com.ecommerce.shop_service.service.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // Cần cái này để check category tồn tại
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        // 1. Check trùng tên sản phẩm (nếu cần)
        if (productRepository.existsByName(request.getName())) {
            throw new RuntimeException("Tên sản phẩm đã tồn tại: " + request.getName());
        }

        // 2. Tìm Category xem có tồn tại không
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại với id: " + request.getCategoryId()));

        // 3. Map Request -> Entity
        Product product = productMapper.toEntity(request);
        product.setCategory(category); // Gán category vào sản phẩm

        // 4. Lưu và trả về
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));

        // Nếu update Category mới thì phải check
        if (request.getCategoryId() != null && !request.getCategoryId().equals(product.getCategory().getId())) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Danh mục mới không tồn tại"));
            product.setCategory(newCategory);
        }

        productMapper.updateEntityFromRequest(request, product);
        product = productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm để xóa");
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<ProductResponse> getAll(Specification<Product> spec, Pageable pageable) {
        Page<Product> pageEntity = productRepository.findAll(spec, pageable);
        Page<ProductResponse> pageDto = pageEntity.map(productMapper::toResponse);
        return new Pagination<>(pageDto);
    }
}