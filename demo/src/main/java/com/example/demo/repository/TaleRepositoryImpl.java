package com.example.demo.repository;

import com.example.demo.model.Tale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Repository
public class TaleRepositoryImpl implements TaleRepository {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static Date firstDate;

    static {
        try {
            firstDate = dateFormat.parse("12/09/2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getRatingByDate(Date date) {
        Date newDate = parseDateIntoCorrectFormat(date);
        long differenceInDays = getDifferenceDates(firstDate, newDate);
        PreparedStatement pstmt = null;
        try (Connection conn = ConnectionFactory.getConnection()) {

            String SQL = "Select * from TaleRating";
            pstmt = conn.prepareStatement(SQL);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id")+" "+ rs.getInt("rating"));
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Tale> getTopTales(int limit) {
        return null;
    }

    @Override
    public Tale getTaleByDate(Date date) {

        Tale tale = null;
        byte[] jsonData;
        Date newDate = parseDateIntoCorrectFormat(date);
        long differenceInDays = getDifferenceDates(firstDate, newDate);
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("static/tales/tale" + differenceInDays + ".txt");
            jsonData = inputStreamToByteArray(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            tale = objectMapper.readValue(jsonData, Tale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tale;
    }

    private Date parseDateIntoCorrectFormat(Date date) {
        String newDateString = dateFormat.format(date);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }


    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        //Inputstream to byte array
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private long getDifferenceDates(Date firstDate, Date secondDate) {
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }


}
