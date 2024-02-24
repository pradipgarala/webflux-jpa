package com.example.webfluxjpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String homeDesc() {
        return "Java Webflux CRUD Application: Department and Employee Management";
    }
}
