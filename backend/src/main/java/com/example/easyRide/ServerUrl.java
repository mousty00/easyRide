package com.example.easyRide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerUrl {
    @Value("${server.base-url}")
    private String baseUrl;

    @Value("${management.server.port}")
    private int port;

    public String getUrl() {
        return "%s:%d".formatted(baseUrl, port);
    }

}
