package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaleController {


    @GetMapping("/hello")
    public String hello() {
        return "hello world!76";
    }
}