package com.example.demo.repository;

import com.example.demo.model.Tale;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleRepository {
    public Tale getTaleByDate(LocalDate date);

    public ObjectNode getRatingByDate(LocalDate date);

    public int addRatingByDate(LocalDate date, int rating);

    public int updateRatingByDate(LocalDate date, int rating,int oldRating);

    public List<Tale> getTopTales(int limit);
}
