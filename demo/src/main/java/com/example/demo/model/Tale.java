package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tale {

    private int id;


    private String title;


    private String description;

    private int rating;

    public Tale() {

    }

    public Tale(String title, String description, int rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public int getRating() {
        return rating;
    }
    @JsonProperty
    public void setRating(int rating) {
        this.rating = rating;
    }
}
