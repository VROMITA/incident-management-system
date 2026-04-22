package com.ims.service;

import com.ims.model.Incident;
import com.ims.repository.IncidentRepository;

import java.time.LocalDateTime;
import java.util.Formattable;
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



        /* System.out.println("=== INCIDENTS BY PRIORITY ===");

        for (Map.Entry<String, Integer> entry : priorityMap.entrySet()){

            System.out.println( entry.getKey() + " : " + entry.getValue() );
*/
        return repository.countByPriority();
        }


    public float getAverageResolutionTime(){

        return repository.averageResolutionTime();
    }

    public List<Incident> getIncidentByDateRange(LocalDateTime from, LocalDateTime to){

        return repository.getIncidentByRangeDate(from, to);


        }

}
