package com.example.demo.model;


import java.util.Date;

/**
 * Created by Csabi on 9/11/2018.
 */
public class Tale {
    private String title;
    private String description;
    private Date dateAdded;
    private int rating;

    public Tale() {

    }

    public Tale(String title, String description, Date dateAdded, int rating) {
        this.title = title;
        this.description = description;
        this.dateAdded = dateAdded;
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

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
