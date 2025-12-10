package com.ecommerce.user_service.service.impl;

import com.ecommerce.user_service.domain.User;
import com.ecommerce.user_service.dto.user.UserCreationDTO;
import com.ecommerce.user_service.dto.user.UserResponseDTO;
import com.ecommerce.user_service.dto.user.UserUpdateDTO;
import com.ecommerce.user_service.repository.UserRepository;
import com.ecommerce.user_service.service.UserService;
import com.ecommerce.user_service.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Sửa chữ ký hàm
    @Override
    public UserResponseDTO createUser(UserCreationDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Dùng Mapper mới
        User user = userMapper.toEntityFromCreationDto(userDTO);

        // Xử lý Password riêng
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user = userRepository.save(user);

        // Trả về Response DTO
        return userMapper.toResponseDto(user);
    }

    // Sửa chữ ký hàm
    @Override
    public UserResponseDTO updateUser(UserUpdateDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Dùng partialUpdate mới
        userMapper.partialUpdateFromUpdateDto(user, userDTO);

        user = userRepository.save(user);
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toResponseDto);
    }

    // UserServiceImpl.java
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).map(userMapper::toResponseDto);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}