package com.example.demo.controller;

import com.example.demo.model.Tale;
import com.example.demo.service.TaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/tale")
public class TaleController {

    @Autowired
    private TaleService taleService;

    @GetMapping("/")
    public Tale getTodaysTale() {
        Tale tale = taleService.getTaleByDate(new Date());
        return tale;
    }
    @GetMapping("/proba")
    public String proba() {

        return "hiiii";
    }
}