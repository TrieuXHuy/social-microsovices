package com.ecommerce.post_service.web.rest;

import com.ecommerce.post_service.client.UserClient;
import com.ecommerce.post_service.domain.Post;
import com.ecommerce.post_service.dto.PostResponse;
import com.ecommerce.post_service.dto.UserDTO;
import com.ecommerce.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserClient userClient;

    // API lấy tất cả bài viết (Đã nâng cấp)
    @GetMapping
    public List<PostResponse> getAllPosts() {
        // 1. Lấy tất cả bài viết từ DB (dạng Entity thô)
        List<Post> posts = postRepository.findAll();

        // 2. Tạo list kết quả
        List<PostResponse> results = new ArrayList<>();

        // 3. Duyệt qua từng bài viết
        for (Post post : posts) {
            PostResponse response = new PostResponse();
            response.setId(post.getId());
            response.setContent(post.getContent());
            response.setUserId(post.getUserId());

            // 4. GỌI ĐIỆN SANG USER SERVICE (Phép thuật nằm ở đây)
            // Lấy thông tin user dựa vào userId của bài viết
            UserDTO user = userClient.getUserById(post.getUserId());

            // 5. Nếu tìm thấy user thì set tên vào
            if (user != null) {
                response.setUserName(user.getUsername());
            }

            results.add(response);
        }

        return results;
    }

    // Giữ nguyên API tạo post...
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }
}