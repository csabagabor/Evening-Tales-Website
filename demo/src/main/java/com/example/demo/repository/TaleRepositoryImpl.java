package com.example.demo.repository;

import com.example.demo.model.Tale;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.DateHelper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.*;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaleRepositoryImpl implements TaleRepository {

    @Override
    public float getRatingByDate(LocalDate date) {
        Map.Entry<Integer, Float> result = getAllRatingDataByDate(date);
        if (result != null)
            return result.getValue();
        return -1;
    }

    private Map.Entry<Integer, Float> getAllRatingDataByDate(LocalDate date) {
        Map.Entry<Integer, Float> resultEntry = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            Date sqlDate = Date.valueOf(date);
            PreparedStatement pstmt = null;
            String SQL = "Select rating from TaleRating where date_added=?";
            pstmt = conn.prepareStatement(SQL);
            pstmt.setDate(1, sqlDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    float rating = rs.getFloat("rating");
                    int nrRating = rs.getInt("nr_rating");
                    resultEntry = new AbstractMap.SimpleEntry<>(nrRating, rating);
                    return resultEntry;
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addRatingByDate(LocalDate date, int rating) {
        float oldRating;
        int nr_rating;
        Map.Entry<Integer, Float> result = getAllRatingDataByDate(date);
        if (result == null)
            return -1;
        oldRating = result.getValue();
        nr_rating = result.getKey();
        float newRating = calculateRating(oldRating, nr_rating, rating);
        try (Connection conn = ConnectionFactory.getConnection()) {
            Date sqlDate = Date.valueOf(date);
            PreparedStatement pstmt = null;
            String SQL = "update TaleRating set rating=? where date_added=?";
            pstmt = conn.prepareStatement(SQL);

            pstmt.setFloat(1, newRating);
            pstmt.setDate(2, sqlDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return 0;
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private float calculateRating(float oldRating, int nr_rating, int rating) {
        float newRating = (oldRating * nr_rating + rating) / (nr_rating + 1);
        return newRating;
    }


    @Override
    public List<Tale> getTopTales(int limit) {
        return null;
    }

    @Override
    public Tale getTaleByDate(LocalDate date) {

        Tale tale = null;
        String correctDateString = DateHelper.parseLocalDateIntoCorrectFormat(date);
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("static/tales/tales.json");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Tale> jsonMap = objectMapper.readValue(inputStream,
                    new TypeReference<Map<String, Tale>>() {
                    });
            tale = jsonMap.get(correctDateString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tale;
    }


}
