package com.ims.service;

import com.ims.model.*;
import com.ims.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class IncidentService {

    private final IncidentRepository repository;


    public IncidentService(){

        this.repository= new IncidentRepository();
    }

    public Incident createIncident(String title, Priority priority, IncidentSource source){

        // Check only title, other values are enum
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("title is invalid");
        }

        Incident incident = new Incident(title, priority, source);

        long hours = switch (priority){
            case CRITICAL -> SlaPolicy.CRITICAL;
            case HIGH -> SlaPolicy.HIGH;
            case MEDIUM -> SlaPolicy.MEDIUM;
            case LOW -> SlaPolicy.LOW;
        };

        incident.setSlaDeadline(incident.getStartDate().plusHours(hours));

        repository.save(incident);

        return incident;
    }

    public List<Incident> allIncidents(){

        return repository.findAll();
    }

    public Optional<Incident> getIncidentById(int id){

        return repository.findById(id);
    }

    public void closeIncident(int id){

        Optional<Incident> optional = repository.findById(id);

        // Check if the incident does not exist
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Incident not found");
        }

        Incident incident = optional.get();

        // Check if the incident is already closed
        if (incident.getStatus() == IncidentStatus.CLOSED){
            throw new IllegalArgumentException("Incident already closed");
        }

        // Change Status and Date
        incident.setStatus(IncidentStatus.CLOSED);
        incident.setEndDate(LocalDateTime.now());
        repository.updateIncident(incident);

    }

    public void deleteIncident(int id){

        Optional<Incident> optional = repository.findById(id);

        if(optional.isEmpty()){
            throw new IllegalArgumentException("Incident not found");
        }

        Incident incident = optional.get();

        repository.deleteIncident(id);
    }

    public void updateIncident(Incident incident){
        repository.updateIncident(incident);
    }



}
