package com.ecommerce.shop_service.web.res;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Shop Service";
    }
}
