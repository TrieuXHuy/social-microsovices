package com.ecommerce.shop_service.repository;

import com.ecommerce.shop_service.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    // Check trùng tên khi tạo mới/update
    boolean existsByName(String name);
}