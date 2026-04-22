package com.ims.service;

import com.ims.repository.IncidentRepository;

import java.util.Formattable;
import java.util.Map;

public class ReportService {

    private final IncidentRepository repository;


    public ReportService(){
        this.repository = new IncidentRepository();

    }


    public void printStatusReport(){

        Map<String, Integer> statusMap = repository.countByStatus();

        System.out.println("=== INCIDENTS BY STATUS ===");

        for (Map.Entry<String, Integer> entry : statusMap.entrySet()){

            System.out.println( entry.getKey() + " : " + entry.getValue() );

        }
    }


    public void printPriorityReport(){

        Map<String, Integer> priorityMap = repository.countByPriority();

        System.out.println("=== INCIDENTS BY PRIORITY ===");

        for (Map.Entry<String, Integer> entry : priorityMap.entrySet()){

            System.out.println( entry.getKey() + " : " + entry.getValue() );

        }
    }

    public void printAverageResolutionTime(){

        float averageTime = repository.averageResolutionTime();

        System.out.println("Average Resolution Time " + averageTime + " hours");
    }
}
