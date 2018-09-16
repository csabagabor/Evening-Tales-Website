package com.example.demo.service;

import com.example.demo.model.Tale;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleService {
    public Tale getTaleByDate(LocalDate date);
    public float getRatingByDate(LocalDate date);
    public int addRatingByDate(LocalDate date, int rating);
    public List<Tale> getTopTales(int limit);
}
