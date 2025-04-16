package com.example.easyRide.pagination;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private List<T> content;

    private int pageNumber;

    @NotNull
    private int size;

    @NotNull
    private boolean firstPage;

    @NotNull
    private boolean lastPage;

    @NotNull
    private int totPages;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.size = page.getSize();
        this.lastPage = page.isLast();
        this.firstPage = page.isFirst();
        this.totPages = page.getTotalPages();
    }
}
