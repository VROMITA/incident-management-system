package com.ims.repository;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
import com.ims.model.IncidentStatus;
import com.ims.model.Priority;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class IncidentRepository {

    private final DatabaseManager dbManager;


    // Call of the DB Manager
    public IncidentRepository(){
             this.dbManager = DatabaseManager.getInstance();
    }


    /**
     * Save the incident in the DB
     * @param incident the incident filled out by the user
     */
    public void save(Incident incident){
       Connection conn = dbManager.getConnection(); // Get a connection with DB
        // INSERT command
       String sql = """
    INSERT INTO incidents (title, status, priority, source, description, assigned_to, start_date, end_date, sla_deadline)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"""; // text block instead of concatenation

         // Delivery the query to the DB
       try {
           PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"id"});  // RETURN_GENERATED_KEYS keeps in memory the generated ID which will be retried with getGeneratedKeys

           // Assign all the ? to the attributes

           stmt.setString(1, incident.getTitle());
           stmt.setString(2, incident.getStatus().name());
           stmt.setString(3, incident.getPriority().name());
           stmt.setString(4, incident.getSource().name());
           stmt.setString(5, incident.getDescription());
           stmt.setString(6, incident.getAssignedTo());
           stmt.setString(7, incident.getStartDate().toString());
           stmt.setString(8, incident.getEndDate() != null ? incident.getEndDate().toString() : null ); // ternary operator to check End Date
           stmt.setString(9, incident.getSlaDeadline() != null ? incident.getSlaDeadline().toString() : null);

           // execute the query
           stmt.executeUpdate();  // Incident created

           // ResultSet is an object which represent the query result.
           ResultSet keys = conn.prepareStatement("SELECT last_insert_rowid()").executeQuery();
           if(keys.next()){
               incident.setId(keys.getInt(1));
           }

           if(keys.next()){
               incident.setId(keys.getInt(1)); // It retries the value of the column 1
           }

       } catch (SQLException e) {
           System.out.println("Error save: " + e.getMessage());
       }


    }

    /**
     * It returns a list of the all incidents
     * @return a list of all incidents
     */
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
                    incident.setEndDate(LocalDateTime.parse(endDate));

                }
                String sla_deadline = rs.getString("sla_deadline");

                if(sla_deadline != null){
                    incident.setSlaDeadline(LocalDateTime.parse(sla_deadline));
                }

                incidents.add(incident);
            }

            return incidents; // return the list of the incidents

        } catch (Exception e) {
            System.out.println("Error on retrieve incidents: " + e.getMessage());
        }

        return incidents; // Second return in case some error occurs, return an empty list with the above error


    }


    /**
     * It returns an Incident or null if the ID cannot be found
     * @param id the ID used for the search
     * @return The incident by ID Or null
     */
    public Optional<Incident> findById(int id){

        Connection conn = dbManager.getConnection();
        String sql = "SELECT * FROM incidents where id = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){

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
                String endDate = rs.getString("end_date");

                if(endDate != null) {
                    incident.setEndDate(LocalDateTime.parse(endDate));
                }
                String sla_deadline = rs.getString("sla_deadline");

                if(sla_deadline != null){
                    incident.setSlaDeadline(LocalDateTime.parse(sla_deadline));
                }

                return Optional.of(incident);
            }

            return Optional.empty(); // id not found


        } catch (SQLException e) {
            System.out.println("Error message finding the id : " +  e.getMessage());
        }

        return Optional.empty(); // error occurs -- empty
    }

    // Method Deleted
    /* public boolean updateStatus(int id, IncidentStatus incidentStatus){

        Connection conn = dbManager.getConnection();
        String sql = "UPDATE incidents SET status = ? WHERE id = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, incidentStatus.name());
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            return rows >0;

        }catch (SQLException e){
            System.out.println("Error updating: " + e.getMessage());
        }

        return false;
    } */

    /**
     * Update the incident in the DB
     * @param incident the Incident that the user wants to update
     * @return true if the update is successful or false if it failed
     */
    public boolean updateIncident(Incident incident){
        Connection conn = dbManager.getConnection();
        String sql = """
                UPDATE incidents SET title = ?, status = ?, priority = ?, source = ?,
                description = ?, assigned_to = ?, start_date = ?, end_date = ?, sla_deadline = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, incident.getTitle());
            stmt.setString(2, incident.getStatus().name());
            stmt.setString(3, incident.getPriority().name());
            stmt.setString(4, incident.getSource().name());
            stmt.setString(5, incident.getDescription());
            stmt.setString(6, incident.getAssignedTo());
            stmt.setString(7, incident.getStartDate().toString());
            stmt.setString(8, incident.getEndDate() != null ? incident.getEndDate().toString() : null ); // Operatore ternario per fare check su End Date
            stmt.setString(9, incident.getSlaDeadline() != null ? incident.getSlaDeadline().toString() : null);
            stmt.setInt(10, incident.getId());

            int rows = stmt.executeUpdate();
            return  rows > 0;

        }catch (SQLException e){
            System.out.println("Error updating values: " + e.getMessage());
        }

        return false; // error


    }

    /**
     * Delete the incident in the DB
     * @param id the ID of the incident that has to be deleted
     * @return true if it is successful or false if not
     */
    public boolean deleteIncident(int id){

        Connection conn = dbManager.getConnection();
        String sql = "DELETE FROM incidents WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows >0;

        }catch (SQLException e){
            System.out.println("Error deleting ID: " + e.getMessage());
        }

        return false;
    }

    /**
     * It return a list that includes a count of incidents based on Status
     * @return a Map with as a Key Status and total as value
     */
    public Map<String, Integer> countByStatus(){

        String sql ="SELECT status, COUNT(*) as total FROM incidents GROUP BY status";
        Map<String, Integer> statusList = new HashMap<>();

        // try-with-resources
        try(Connection conn = dbManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){

                statusList.put(rs.getString("status"), rs.getInt("total") );

            }

        } catch (SQLException e) {

            System.out.println("Error occured counting the incidents by status" + e.getMessage());

        }
        return statusList;

    }

    public Map<String, Integer> countByPriority(){

        String sql ="SELECT priority, COUNT(*) as total FROM incidents GROUP BY priority";
        Map<String, Integer> priorityList = new HashMap<>();

        try(Connection conn = dbManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()){

                priorityList.put(rs.getString("priority"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving priority list " + e.getMessage());
        }

        return priorityList;
    }

    public float averageResolutionTime(){

        String sql = "SELECT AVG(julianday(end_date) - julianday(start_date)) * 24 AS average_hours FROM incidents WHERE end_date IS NOT null";

        float averageTime = 0;

        try(Connection conn = dbManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){

            if (rs.next()){

                averageTime = rs.getFloat("average_hours");
            }

        }catch(SQLException e){

            System.out.println("Error retriving average resolution :" + e.getMessage());
        }

        return averageTime;
    }

}
