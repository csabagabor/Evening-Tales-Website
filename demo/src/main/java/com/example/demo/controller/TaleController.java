package com.example.demo.controller;

import com.example.demo.model.Tale;
import com.example.demo.service.TaleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/tale")
public class TaleController {

    @Autowired
    private TaleService taleService;
    private final String dateFormat = DateHelper.correctDatePattern;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/")
    public Tale getTodaysTale() {
        Tale tale = taleService.getTaleByDate(LocalDate.now());
        return tale;
    }

    @GetMapping("/rating/{date}")
    public float getRatingByLocalDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date) {
        float rating = taleService.getRatingByDate(date);
        return rating;
    }


    @PutMapping("/rating/{date}")
    public String addRatingByLocalDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date,
                                       @RequestBody String body) throws IOException {
        JsonNode jsonNode = mapper.readTree(body);
        int rating = jsonNode.get("rating").intValue();

        int returnValue = taleService.addRatingByDate(date, rating);
        return returnValue > -1 ? "Success" : "Fail";
    }

}