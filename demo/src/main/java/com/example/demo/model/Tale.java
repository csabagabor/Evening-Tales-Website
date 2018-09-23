package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/*
 select * from TaleRating;
*/

/*
CREATE TABLE TaleRating (
  id              SERIAL PRIMARY KEY,
  date_added         date NOT NULL,
  rating REAL NOT NULL,
  nr_rating INT NOT NULL
);
 */
/*
INSERT INTO TaleRating (date_added , rating, nr_rating)
VALUES ('2018-09-16', 0, 0);
 */
/*
INSERT INTO TaleRating (date_added , rating, nr_rating)
SELECT date::date, 0, 0
from generate_series(
  '2018-09-16'::date,
  '2018-09-20'::date,
  '1 day'::interval
) date;
 */
public class Tale {

    private int id;


    private String title;


    private String description;

    private int rating;

    @JsonIgnore
    private String dateAdded;

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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
