package com.ims.service;

import com.ims.model.Incident;
import com.ims.repository.IncidentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportService {

    private final IncidentRepository repository;


    public ReportService(){
        this.repository = new IncidentRepository();

    }

    public Map<String, Integer> getStatusReport(){

        return  repository.countByStatus();
    }


    public Map<String, Integer>  getPriorityReport(){

        return repository.countByPriority();
        }


    public float getAverageResolutionTime(){

        return repository.averageResolutionTime();
    }

    public List<Incident> getIncidentByDateRange(LocalDateTime from, LocalDateTime to){

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return repository.getIncidentByRangeDate(from, to);
        }

}
