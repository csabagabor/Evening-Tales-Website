package com.example.demo.service;

import com.example.demo.model.Tale;
import com.example.demo.repository.TaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaleServiceImpl implements TaleService {


    @Autowired
    TaleRepository taleRepository;

    @Override
    public Tale getTaleByDate(Date date) {
        return taleRepository.getTaleByDate(date);
    }

    @Override
    public List<Tale> getTopTales(int limit) {
        return null;
    }
}
