package com.example.easyRide.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(path = "/")
    public String index(Model model) {
        return "index";
    }
}
