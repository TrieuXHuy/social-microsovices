package com.ecommerce.user_service.web.rest;

import com.ecommerce.common.dto.PageResponse;
import com.ecommerce.user_service.domain.User;
import com.ecommerce.user_service.dto.user.UserCreationDTO; // DTO mới
import com.ecommerce.user_service.dto.user.UserResponseDTO; // DTO mới
import com.ecommerce.user_service.dto.user.UserUpdateDTO; // DTO mới
import com.ecommerce.user_service.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @PostMapping("/users")
    // 👇 Dùng UserCreationDTO
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreationDTO userDTO) {
        log.debug("REST request to save User : {}", userDTO);

        UserResponseDTO result = userService.createUser(userDTO);

        return ResponseEntity
                .created(URI.create("/api/users/" + result.getId()))
                .body(result);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userDTO
    ) {
        log.debug("REST request to update User : {}, {}", id, userDTO);

        if (userDTO.getId() != null && !id.equals(userDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID (URL ID does not match Body ID)");
        }

        userDTO.setId(id);

        UserResponseDTO result = userService.updateUser(userDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserResponseDTO>> getAllUsers( // 👈 Thay đổi kiểu trả về
                                                                      @Filter Specification<User> spec,
                                                                      Pageable pageable
    ) {
        log.debug("REST request to get a page of Users");

        Page<UserResponseDTO> page = userService.getAllUsers(spec, pageable);

        PageResponse<UserResponseDTO> response = new PageResponse<>(page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}