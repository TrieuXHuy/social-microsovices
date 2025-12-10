package com.ecommerce.post_service.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;   // Nội dung status
    private String imageUrl;  // Link ảnh (nếu có)

    private Long userId;      // ID người đăng (Liên kết lỏng với User Service)

    private Integer likeCount; // Đếm số like (Đơn giản hóa trước)

    private LocalDateTime createdAt; // Thời gian đăng

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if(this.likeCount == null) this.likeCount = 0;
    }
}