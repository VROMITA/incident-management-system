package com.ims.service;

import com.ims.model.Incident;
import com.ims.model.IncidentStatus;
import com.ims.model.Priority;
import com.ims.model.SlaStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SlaMonitor {
    /**
     * It returns a classification of the incident based on the priority - BREACH - AT_RISK - OK
     * the threshold is 6 hours - AT_RISK_THRESHOLD - and the remaining time and classification is based on this timeframe
     * @param incident the Incident that has to be classified
     * @return the classification of the incident
     */
    public SlaStatus classify(Incident incident){

          // CHECK if incident is close
      if(incident.getStatus() == IncidentStatus.CLOSED){
          return SlaStatus.OK;


      }
        // Time in hours remaining before the BREACH
      long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());

        // CHECK BREACH
      if (LocalDateTime.now().isAfter(incident.getSlaDeadline())) {
          return SlaStatus.BREACH;
          // CHECK if it is CRITICAL
      } else if (incident.getPriority() == Priority.CRITICAL){

          return SlaStatus.AT_RISK;
          // CHECK if it due in less than 6 hours
      } else if (hoursLeft < SlaPolicy.AT_RISK_THRESHOLD) {

          return SlaStatus.AT_RISK;

      }else
          return  SlaStatus.OK;
    }
}
