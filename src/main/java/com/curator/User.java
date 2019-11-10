package com.curator;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class User {
    //TODO: create database to save user playlist, favorites, settings
    // + other preferences/data for recommendation model


    public static void main(String[] args) {
        String filename = "user.db";
        String q =
                "CREATE TABLE playlists (" +
                "trackId TEXT PRIMARY KEY," +
                "playlist TEXT NOT NULL," +
                "sequence INTEGER NOT NULL," +
                "albumString TEXT," +
                "name TEXT," +
                "popularity INTEGER," +
                "trackNumber INTEGER," +
                "durationString INTEGER," +
                "localPath TEXT" +
                ");";

        String path = new File("src/main/res/dbs/" + filename).getAbsolutePath();
        String url = "jdbc:sqlite:" +  path;



        try(Connection conn = DriverManager.getConnection(url)){
            if (conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println(meta.getDriverName());

                String s = "INSERT INTO playlists " +
                        "(trackId, playlist, sequence, albumString, name, popularity, trackNumber,durationString," +
                        "localPath)" +
                        "VALUES (?,?,?,?,?,?,?,?,?)"
                        ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
