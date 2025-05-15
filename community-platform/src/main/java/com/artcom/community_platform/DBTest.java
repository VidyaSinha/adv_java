package com.artcom.community_platform;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/communitydb";
            String username = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
