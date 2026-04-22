package com.ims.service;

import com.ims.model.*;
import com.ims.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class IncidentService {

    private final IncidentRepository repository;
    private final SlaMonitor slaMonitor;
    private static final Logger logger = Logger.getLogger(IncidentService.class.getName());


    public IncidentService() {
        this.slaMonitor = new SlaMonitor();
        this.repository = new IncidentRepository();
    }

    /**
     * Create a new incident and calculate the SLA deadline based on priority
     * @param title the incident title cannot be blank or null
     * @param priority the SLA deadline is calculated based on the priority
     * @param source the source is the reporter user
     * @return the created incident with an ID and the SLA deadline
     */
    public Incident createIncident(String title, Priority priority, IncidentSource source) {

        // Check only title, other values are enum
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is invalid");
        }

        Incident incident = new Incident(title, priority, source);

        long hours = switch (priority) {
            case CRITICAL -> SlaPolicy.CRITICAL;
            case HIGH -> SlaPolicy.HIGH;
            case MEDIUM -> SlaPolicy.MEDIUM;
            case LOW -> SlaPolicy.LOW;
        };

        incident.setSlaDeadline(incident.getStartDate().plusHours(hours));

        repository.save(incident);
        logger.info("Incident created: ID=" + incident.getId() + " | " + incident.getTitle() + " | " + priority);

        return incident;
    }

    /**
     * The output is a list of the all incidents
     * @return a list of the incidents
     */
    public List<Incident> allIncidents() {

        return repository.findAll();
    }

    /**
     * A search by using the ID - if the user does not know the ID can use the allIncidents method
     * @param id the ID of the incident
     * @return the incident with the searched ID
     */
    public Optional<Incident> getIncidentById(int id) {

        return repository.findById(id);
    }

    /**
     * It close the incident with validation
     * @param id The incident ID that the user wants to close
     */
    public void closeIncident(int id) {

        Optional<Incident> optional = repository.findById(id);

        // Check if the incident does not exist
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Incident not found");
        }

        Incident incident = optional.get();

        // Check if the incident is already closed
        if (incident.getStatus() == IncidentStatus.CLOSED) {
            logger.warning("Attempt to close already CLOSED incident: ID=" + id);
            throw new IllegalArgumentException("Incident already closed");

        }

        // Change Status and Date
        incident.setStatus(IncidentStatus.CLOSED);
        incident.setEndDate(LocalDateTime.now());
        repository.updateIncident(incident);
        logger.info("Incident closed: ID=" + id);

    }

    /**
     * Delete the incident by the ID
     * @param id the ID of the incident that has to be deleted
     */
    public void deleteIncident(int id) {

        Optional<Incident> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Incident not found");
        }

        Incident incident = optional.get();

        if (repository.deleteIncident(id)) {

            logger.warning("Incident deleted: ID=" + id);

        }else {
            logger.warning("Deletion terminated unsuccessfully ID=" + id);
        }
    }

    /**
     * Update the values of the incident
     * @param incident The incidents that the user wants to modify
     */
    public void updateIncident(Incident incident) {

        if (repository.updateIncident(incident)) {
            logger.info("Incident updated: ID=" + incident.getId());
        }else {
            logger.warning("Incident NOT updated: ID= " + incident.getId());
        }

    }

    /**
     * Checks the SLA status of all open incidents and classifies them
     * as BREACH, AT_RISK or OK based on their deadline and priority.
     *
     * @return a Map with SlaStatus as key and the list of incidents for each status
     */
    public Map<SlaStatus, List<Incident>> checkSlaStatus() {
        logger.info("SLA status check executed");
        // Create 3 Empty List with the assigned status
        Map<SlaStatus, List<Incident>> result = new HashMap<>();
        result.put(SlaStatus.BREACH, new ArrayList<>());
        result.put(SlaStatus.AT_RISK, new ArrayList<>());
        result.put(SlaStatus.OK, new ArrayList<>());

        List<Incident> allIncident = repository.findAll();

        // iterate through all the incident - classify them and assign to the correct ArrayList
        for(Incident incident : allIncident ){

            SlaStatus status = slaMonitor.classify(incident);
            result.get(status).add(incident);

        }

        return result;

    }

}