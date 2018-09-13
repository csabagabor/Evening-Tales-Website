package com.example.demo.controller;

import com.example.demo.model.Tale;
import com.example.demo.service.TaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/rating/{date}")
    public int getRatingByDate(@PathVariable @DateTimeFormat(pattern="MM-dd-yyyy") Date date) {
        int rating = taleService.getRatingByDate(date);
        return rating;
    }

}