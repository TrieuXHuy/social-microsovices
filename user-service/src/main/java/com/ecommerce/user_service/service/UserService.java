package com.ecommerce.user_service.service;

import com.ecommerce.user_service.domain.User;
import com.ecommerce.user_service.dto.user.UserCreationDTO;
import com.ecommerce.user_service.dto.user.UserResponseDTO;
import com.ecommerce.user_service.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UserService {

    UserResponseDTO createUser(UserCreationDTO userDTO);

    UserResponseDTO updateUser(UserUpdateDTO userDTO);

    Optional<UserResponseDTO> getUserById(Long id);

    Page<UserResponseDTO> getAllUsers(Specification<User> spec, Pageable pageable);

    void deleteUser(Long id);
}