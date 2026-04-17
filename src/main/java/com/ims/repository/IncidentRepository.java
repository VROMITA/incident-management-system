package com.ims.repository;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
import com.ims.model.IncidentStatus;
import com.ims.model.Priority;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IncidentRepository {

    private final DatabaseManager dbManager;


    // Call of the DB Manager
    public IncidentRepository(){
             this.dbManager = DatabaseManager.getInstance();
    }


    // Method to save the Incident into DB
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

           // ResultSet is an object which represent the query result.
           ResultSet keys=stmt.getGeneratedKeys();

           if(keys.next()){
               incident.setId(keys.getInt(1)); // It retries the value of the column 1
           }

       } catch (SQLException e) {
           System.out.println("Error save: " + e.getMessage());
       }


    }

    // Method to retrieve all the incident and output as list
    public List<Incident> findAll(){

        List<Incident> incidents = new ArrayList<>();
        Connection conn = dbManager.getConnection();
        String sql = "SELECT * FROM incidents";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();


            // For the attributes not included in the constructor as parameter the setter is used
            while(rs.next()) {

                Incident incident = new Incident(

                rs.getString("title"),
                Priority.valueOf(rs.getString("priority")),
                IncidentSource.valueOf(rs.getString("source"))
                );

                incident.setId(rs.getInt("id"));
                incident.setStatus(IncidentStatus.valueOf(rs.getString("status")));
                incident.setDescription(rs.getString("description"));
                incident.setAssignedTo(rs.getString("assigned_to"));
                incident.setStartDate(LocalDateTime.parse(rs.getString("start_date")));

                // End date can be null in the DB therefore a variable is declared to avoid DB crash and is checked before the set method with an If
                String endDate = rs.getString("end_date");
                if(endDate != null) {
                    incident.setEndDate(LocalDateTime.parse(rs.getString("end_date")));

                }

                incidents.add(incident);
            }

            return incidents; // return the list of the incidents

        } catch (Exception e) {
            System.out.println("Error on retrieve incidents: " + e.getMessage());
        }

        return incidents; // Second return in case some error occurs, return an empty list with the above error


    }

}
