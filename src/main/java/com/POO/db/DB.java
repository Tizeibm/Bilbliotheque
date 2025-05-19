package com.POO.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static String dbUrl = "jdbc:sqlite:library.db";
    private static Connection conn = null;

    public static void connect() {
        disconnect();
        try {
            conn = DriverManager.getConnection(dbUrl);
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}
