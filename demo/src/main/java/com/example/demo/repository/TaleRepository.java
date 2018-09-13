package com.example.demo.repository;

import com.example.demo.model.Tale;

import java.util.Date;
import java.util.List;

/**
 * Created by Csabi on 9/11/2018.
 */
public interface TaleRepository {
    public Tale getTaleByDate(Date date);

    public int getRatingByDate(Date date);

    public List<Tale> getTopTales(int limit);
}
