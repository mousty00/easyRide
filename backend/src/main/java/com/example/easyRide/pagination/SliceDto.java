package com.example.easyRide.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@NoArgsConstructor
public class SliceDto<T> {

    private Info info;
    private List<T> results;

    public SliceDto(Slice<T> page, String urlPath) {
        int count = page.getNumberOfElements();
        String next = page.hasNext() ? String.format("%s?page=%d", urlPath, page.getPageable().getPageNumber() + 1) : null;
        String prev = page.hasPrevious() ? String.format("%s?page=%d", urlPath, page.getPageable().getPageNumber() - 1) : null;
        int pages = totalPages(page);
        this.info = new Info(count, pages, next, prev);
        this.results = page.getContent();
    }

    private int totalPages(Slice<T> slice) {
        Pageable pageable = slice.getPageable();
        int pageSize = pageable.getPageSize();
        long totalElements = (long) pageable.getPageNumber() * pageSize + slice.getNumberOfElements();
        return (int) Math.ceil((double) totalElements / pageSize);
    }
}
