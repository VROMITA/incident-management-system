package com.ims.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Utility class for database connection management.
 * Uses static methods since the class is stateless.
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:incidents.db";

    // Private constructor to prevent instantiation
    private DatabaseManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

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
