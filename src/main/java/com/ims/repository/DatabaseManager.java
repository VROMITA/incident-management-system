package com.ims.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* DONE:
 the singleton here doesn’t add value because the class is stateless and creates a new connection every time.
 you could simplify this by removing the singleton or move toward dependency injection.
 */

/**
 * Utility class for database connection management.
 * Uses static methods since the class is stateless.
 */
public class DatabaseManager {

    // DONE in Spring: in general you will see, specially in spring boot, that this kind of info should be stored in properties/env file to avoid exposing sensitive data inside the code directly
    private static final String DB_URL = "jdbc:sqlite:incidents.db";

    // Private constructor to prevent instantiation
    private DatabaseManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // DONE: this is the singleton pattern
    /**
     * Returns a new database connection.
     * Note: Caller is responsible for closing the connection.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Creates the incidents table if it doesn't exist.
     */
    public static void createTable(){
        String sql = """
                CREATE TABLE IF NOT EXISTS incidents (
                 id          INTEGER PRIMARY KEY AUTOINCREMENT,
                 title       TEXT NOT NULL,
                 status      TEXT NOT NULL,
                 priority    TEXT NOT NULL,
                 source      TEXT NOT NULL,
                 description TEXT,
                 assigned_to TEXT,
                 start_date  TEXT NOT NULL,
                 end_date    TEXT,
                 sla_deadline TEXT
           
              );
           """;

        try(Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.createStatement().execute(sql);
            System.out.println("Incidents table ready!");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
}
