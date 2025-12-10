package com.ecommerce.post_service.dto;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String content;
    private Long userId;
    private String userName; // <--- Đây là cái mình muốn thêm vào
}