package com.example.demo.controller;

import com.example.demo.model.Tale;
import com.example.demo.service.TaleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;
import helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.monitorjbl.json.Match.match;


@RestController
@RequestMapping("/api/tale")
public class TaleController {

    @Autowired
    private TaleService taleService;
    private final String dateFormat = DateHelper.correctDatePattern;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JsonViewModule());

    @GetMapping("/{date}")
    public Tale getTaleByDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date) {
        Tale tale = taleService.getTaleByDate(date);
        return tale;
    }

    @GetMapping("/")
    public Tale getTodaysTale() {
        Tale tale = taleService.getTaleByDate(LocalDate.now());
        return tale;
    }

    @GetMapping(value = "/rating/{date}", produces = "application/json")
    public String getRatingByDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date) {
        ObjectNode ratingNode = taleService.getRatingByDate(date);
        return ratingNode.toString();
    }


    @PutMapping(value = "/rating/{date}", produces = "application/json")
    public String updateRatingByDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date,
                                     @RequestBody String body) throws IOException {
        JsonNode jsonNode = mapper.readTree(body);
        int rating = jsonNode.get("rating").intValue();
        int oldRating = jsonNode.get("oldRating").intValue();
        int returnValue = taleService.updateRatingByDate(date, rating, oldRating);
        ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
        String errorMsg = null;
        switch (returnValue) {
            case -1:
                errorMsg = "Fail";
                break;
            case -2:
                errorMsg = "Rating must be between 1 and 5";
                break;
            case -3:
                errorMsg = "Cannot connect to database";
                break;
            case -4:
                errorMsg = "Cannot find your rating! First post your rating!";
                break;

            default:
                errorMsg = "Success";

        }
        resultNode.put("message", errorMsg);
        resultNode.put("returnNumber", returnValue);
        return resultNode.toString();
    }

    @PostMapping(value = "/rating/{date}", produces = "application/json")
    public String addRatingByDate(@PathVariable @DateTimeFormat(pattern = dateFormat) LocalDate date,
                                  @RequestBody String body) throws IOException {
        JsonNode jsonNode = mapper.readTree(body);
        int rating = jsonNode.get("rating").intValue();

        int returnValue = taleService.addRatingByDate(date, rating);
        ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
        String errorMsg = null;
        switch (returnValue) {
            case -1:
                errorMsg = "Fail";
                break;
            case -2:
                errorMsg = "Rating must be between 1 and 5";
                break;
            case -3:
                errorMsg = "Cannot connect to database";
                break;

            default:
                errorMsg = "Success";
        }
        resultNode.put("message", errorMsg);
        resultNode.put("returnNumber", returnValue);
        return resultNode.toString();
    }


    @GetMapping(value = "/top/{limit}", produces = "application/json")
    public String getTopTales(@PathVariable int limit) throws JsonProcessingException {

        List<Tale> list = taleService.getTopTales(limit);
        String json = mapper.writeValueAsString(JsonView.with(list).onClass(Tale.class, match().include("dateAdded")));
        return json;
    }


}