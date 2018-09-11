package com.example.demo.repository;

import com.example.demo.model.Tale;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Repository
public class TaleRepositoryImpl implements TaleRepository {
    @Override
    public Tale getTaleByDate(Date date) {

        Tale tale = null;
        byte[] jsonData = new byte[0];
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            InputStream inputStream = cl.getResourceAsStream("static/tales/tale1.txt");
            jsonData = inputStream.readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            tale = objectMapper.readValue(jsonData, Tale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tale;
    }

    @Override
    public List<Tale> getTopTales(int limit) {
        return null;
    }


}
