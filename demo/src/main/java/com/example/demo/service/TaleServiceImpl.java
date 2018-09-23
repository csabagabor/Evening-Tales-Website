package com.example.demo.service;

import com.example.demo.model.Tale;
import com.example.demo.repository.TaleRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaleServiceImpl implements TaleService {


    @Autowired
    TaleRepository taleRepository;

    @Override
    public Tale getTaleByDate(LocalDate date) {
        return taleRepository.getTaleByDate(date);
    }

    @Override
    public ObjectNode getRatingByDate(LocalDate date) {
        return taleRepository.getRatingByDate(date);
    }

    @Override
    public int addRatingByDate(LocalDate date, int rating) {
        return taleRepository.addRatingByDate(date, rating);
    }

    @Override
    public int updateRatingByDate(LocalDate date, int rating, int oldRating) {
        return taleRepository.updateRatingByDate(date,rating,oldRating);
    }

    @Override
    public List<Tale> getTopTales(int limit) {
        return taleRepository.getTopTales(limit);
    }
}
