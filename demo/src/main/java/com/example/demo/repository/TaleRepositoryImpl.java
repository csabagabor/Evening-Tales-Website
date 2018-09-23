package com.example.demo.repository;

import com.example.demo.model.Tale;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helper.DateHelper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TaleRepositoryImpl implements TaleRepository {

    private PreparedStatement createPreparedStatement(Connection conn, String SQLquery) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(SQLquery);
        return pstmt;
    }

    private ObjectNode getAllRatingDataByDate(LocalDate date) {
        ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String SQL = "Select * from TaleRating where date_added=?";
            PreparedStatement pstmt = createPreparedStatement(conn, SQL);
            pstmt.setObject(1, date);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    float rating = rs.getFloat("rating");
                    int nrRating = rs.getInt("nr_rating");
                    resultNode.put("rating", rating);
                    resultNode.put("nr_rating", nrRating);
                    return resultNode;
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
    public ObjectNode getRatingByDate(LocalDate date) {
        ObjectNode result = getAllRatingDataByDate(date);
        return result;
    }

    /*
    update TaleRating set rating=0, nr_rating=0 where date_added='2018-09-16'
     */
    @Override
    public int addRatingByDate(LocalDate date, int rating) {
        if (rating < 1 || rating > 5)
            return -2;
        ObjectNode result = getAllRatingDataByDate(date);
        if (result == null)
            return -3;
        float overallRating = result.get("rating").floatValue();
        int nrRating = result.get("nr_rating").intValue();
        float newRating = calculateRating(overallRating, nrRating, rating);
        nrRating++;
        return saveRatingToDatabase(date, nrRating, newRating);
    }

    private int saveRatingToDatabase(LocalDate date, int nrRating, float newRating) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String SQL = "update TaleRating set rating=?,nr_rating=? where date_added=?";
            PreparedStatement pstmt = createPreparedStatement(conn, SQL);
            pstmt.setFloat(1, newRating);
            pstmt.setInt(2, nrRating);
            pstmt.setObject(3, date);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 1) {
                return 0;
            }
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int updateRatingByDate(LocalDate date, int rating, int oldRating) {
        if (rating < 1 || rating > 5)
            return -2;
        ObjectNode result = getAllRatingDataByDate(date);
        if (result == null)
            return -3;
        float overallRating = result.get("rating").floatValue();
        int nrRating = result.get("nr_rating").intValue();
        if (nrRating < 1)//cannot update rating if no rating is present
            return -4;
        float newRating = calculateRating(overallRating, nrRating, rating, oldRating);
        return saveRatingToDatabase(date, nrRating, newRating);
    }

    private float calculateRating(float overallRating, int nrRating, int rating, int oldRating) {
        float newRating = (overallRating * nrRating - oldRating + rating) / nrRating;
        return newRating;
    }

    private float calculateRating(float overallRating, int nrRating, int rating) {
        float newRating = (overallRating * nrRating + rating) / (nrRating + 1);
        return newRating;
    }


    @Override
    public List<Tale> getTopTales(int limit) {
        List<Tale> resultList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String SQL = "Select date_added from TaleRating order by rating DESC LIMIT ?";
            PreparedStatement pstmt = createPreparedStatement(conn, SQL);
            pstmt.setInt(1, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate dateAdded = rs.getDate("date_added").toLocalDate();
                    Tale tale = getTaleByDate(dateAdded);
                    if(tale!=null) {
                        tale.setDateAdded(DateHelper.parseLocalDateIntoCorrectFormat(dateAdded));
                        resultList.add(tale);
                    }
                }
                return resultList;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
