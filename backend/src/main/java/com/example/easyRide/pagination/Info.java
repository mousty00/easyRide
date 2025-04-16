package com.example.easyRide.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
