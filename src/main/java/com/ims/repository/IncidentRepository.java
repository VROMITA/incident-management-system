package com.ims.repository;

import com.ims.model.Incident;

import java.sql.*;

public class IncidentRepository {

    private final DatabaseManager dbManager;


    private IncidentRepository(){
             this.dbManager = DatabaseManager.getInstance();
    }

    public void save(Incident incident){
       Connection conn = dbManager.getConnection(); // Get a connection with DB
        // INSERT command
       String sql = """
    INSERT INTO incidents (title, status, priority, source, description, assigned_to, start_date, end_date)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)"""; // text block instead of concatenation

         // Delivery the query to the DB
       try {
           PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  // RETURN_GENERATED_KEYS keeps in memory the generated ID which will be retried with getGeneratedKeys

           // Assign all the ? to the attributes

           stmt.setString(1, incident.getTitle());
           stmt.setString(2, incident.getStatus().name());
           stmt.setString(3, incident.getPriority().name());
           stmt.setString(4, incident.getSource().name());
           stmt.setString(5, incident.getDescription());
           stmt.setString(6, incident.getAssignedTo());
           stmt.setString(7, incident.getStartDate().toString());
           stmt.setString(8, incident.getEndDate() != null ? incident.getEndDate().toString() : null ); // Operatore ternario per fare check su End Date

           // execute the query
           stmt.executeUpdate();  // Incident created

           ResultSet keys=stmt.getGeneratedKeys();

           if(keys.next()){
               incident.setId(keys.getInt(1)); // It retries the value of the column 1
           }

       } catch (SQLException e) {
           System.out.println("Error save: " + e.getMessage());
       }


    }


}
