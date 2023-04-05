package dev.andersonfernandes.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(getDatabaseUrl());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection == null) return;

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDatabaseUrl() {
        Dotenv dotenv = Dotenv.configure().load();
        return dotenv.get("LIBRARY_MANAGER_DATABASE_URL");
    }
}
