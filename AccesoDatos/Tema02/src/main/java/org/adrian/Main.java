package org.adrian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    static String DB_DRIVER = "postgresql";
    static String DB_HOST = "localhost";
    static String DB_PORT = "5434";
    static String DB_USER = "admin";
    static String DB_PASSWORD = "admin123";
    static String DB_NAME = "ciclismo_db";

    public static void main(String[] args) {
        String sql = "SELECT * FROM ciclistas";
        String url = "jdbc:" + DB_DRIVER + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        try (Connection conexion = DriverManager.getConnection(url, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = conexion.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        System.out.println(rs.getString("nombre") + " " + rs.getString("pais"));
                    }
                }
            }
            System.out.println("Conectado a la base de datos");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
    }
}