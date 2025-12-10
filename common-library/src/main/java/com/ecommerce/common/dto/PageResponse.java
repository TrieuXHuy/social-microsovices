package com.ecommerce.common.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import java.io.Serializable;
import java.util.List;

@Data
public class PageResponse<T> implements Serializable {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<T> data;


    public PageResponse() {
    }

    public PageResponse(Page<T> page) {
        this.data = page.getContent();
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}