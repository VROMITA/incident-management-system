package com.ims.service;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
import com.ims.model.IncidentStatus;
import com.ims.model.Priority;
import com.ims.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IncidentService {

    private final IncidentRepository repository;

    public IncidentService(){

        this.repository= new IncidentRepository();
    }

    public Incident createIncident(String title, Priority priority, IncidentSource source){

        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("title is invalid");
        }

        Incident incident = new Incident(title, priority, source);
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
        if(optional.isEmpty()){
            throw new IllegalArgumentException("Incident not found");
        }
        Incident incident = optional.get();
        if (incident.getStatus() == IncidentStatus.CLOSED){
            throw new IllegalArgumentException("Incident already closed");
        }
        incident.setStatus(IncidentStatus.CLOSED);
        incident.setEndDate(LocalDateTime.now());
        repository.updateStatus(id, IncidentStatus.CLOSED);

    }


}
