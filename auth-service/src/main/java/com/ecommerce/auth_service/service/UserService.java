package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.domain.User;
import com.ecommerce.auth_service.dto.user.UserRequest;
import com.ecommerce.auth_service.dto.user.UserResponse;
import com.ecommerce.common.dto.Pagination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    UserResponse createUser(UserRequest request);

    Pagination<UserResponse> getAllUsers(Specification<User> spec, Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}