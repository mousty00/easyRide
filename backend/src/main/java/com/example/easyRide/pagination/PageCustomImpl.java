package com.example.easyRide.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageCustomImpl<T> extends PageImpl<T> {
    @JsonProperty("results")
    List<T> content;

    public PageCustomImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

}
