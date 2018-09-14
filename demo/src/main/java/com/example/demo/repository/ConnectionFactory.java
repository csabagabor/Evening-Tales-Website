package com.example.demo.repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {


    public static Connection getConnection() throws URISyntaxException, SQLException {
        URI dbUri = null;
        String databaseUrl = System.getenv("DATABASE_URL");
        if(databaseUrl == null){
            //local setup
            dbUri = new URI("postgres://jaqyspwadaepgk:f74d0f8dbee50d751e8d9095efb5e62ed5e07df76c7681c6e142da0311a5fa1c@ec2-23-23-253-106.compute-1.amazonaws.com:5432/d1c92ljjeq05k1");
        }
        else{
            dbUri = new URI(databaseUrl);
        }




        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
