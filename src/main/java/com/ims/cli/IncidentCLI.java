package com.ims.cli;

import com.ims.model.*;
import com.ims.service.IncidentService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/* TODO: in general classes should be at max of 300 rows for best practice, refactor this class to obtain this result, but since you will switch to
 spring boot app, is ok. */
/*
    TODO: in general this class is named CLI but is not doing only that, for example the method updateIncident() is doing a lot of stuff and it is too long
    consider creating separate service for the logic, bcause method should be max 30 rows long.
    also, since it is doing a lot of different stuff, consider creating a method for each of them
 */

/*
TODO: in general this class is breaking the SRP principle (single responsability principle) because it does not do one single thing, but:
    1) input handling (Scanner)
    2) business logic (validations, SLA logic, updates)
    3) formatting/output
    4) orchestration
    also, in the menu display, you are hardcoding the options "1, 2,3 " ecc... think about using an enum selector class
  */
public class IncidentCLI {
     private final Scanner scanner = new Scanner(System.in); // TODO: avoid using/creating Scanner for both ReportCLI and IncidentCLI

    /*
       TODO: in general, do not create service classes like this = new Service() but prefer using the singleton design pattern (same for other service classes) focusing on DI (dependency injection principle)
       p.s. you will see this a lot in spring/spring boot application
     */
    private final IncidentService service = new IncidentService();

     private final ReportCLI reportCLI = new ReportCLI();

     public void start(){
         System.out.println("Welcome to the Incident Management System\n");
         showMenu();

     }

     private void showMenu(){

         int input;

         do {
             System.out.println("\n1 - Create a new incident");
             System.out.println("2 - View all the incidents");
             System.out.println("3 - Find an incident by ID");
             System.out.println("4 - Delete an incident");
             System.out.println("5 - Update an incident");
             System.out.println("6 - Check SLA status");
             System.out.println("7 - Report options");
             System.out.println("8 - Exit");

             System.out.println("Choose an option: ");

             try {
                 input = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number.");
                 input = 0;
             }

             switch (input){

                 case 1:
                     createIncident();
                     break;

                 case 2:
                     listAllIncidents();
                     break;

                 case 3:
                     findIncidentById();
                     break;

                 case 4:
                     deleteIncidentById();
                     break;

                 case 5:
                     updateIncident();
                     break;

                 case 6:
                     checkSlaStatus();
                     break;

                 case 7:
                     reportCLI.start();
                     break;

                 case 8 :
                     break;


                 default:
                     System.out.println("Invalid option. Try again.\n");
             }

         }while (input !=8 );

         System.out.println("Bye Bye!\n");

     }

     private void createIncident(){

         System.out.println("Incident Creation\n");

         // Start fill out data

         // Title
         String title;
         do {
             System.out.println("Choose a title: ");
             title = scanner.nextLine().trim();
             if (title.isBlank()) {
                 System.out.println("Title cannot be empty. Try again.");
             }
         } while (title.isBlank());

         // Priority // TODO: what if I add another priority? I will need to remember to update also this menu, please loop the proprity values to dinamically get them
         System.out.println("Select priority:");
         System.out.println("1 -LOW");
         System.out.println("2 -MEDIUM");
         System.out.println("3 -HIGH");
         System.out.println("4 -CRITICAL");

         int priority;
         Priority selectedPriority = null;

         do {
             try {
                 priority = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number.");
                 priority = 0;
             }

         /* TODO: what about adding a property to the enum that will represent the cases?
            for example: priority value and you get the "LOW, MEDIUM" and so on via 1,2,3 ecc...
          */
         switch (priority){
             case 1:
                 selectedPriority = Priority.LOW;
                 break;
             case 2:
                 selectedPriority = Priority.MEDIUM;
                 break;
             case 3:
                 selectedPriority = Priority.HIGH;
                 break;
             case 4:
                 selectedPriority = Priority.CRITICAL;
                 break;

             default:
                 System.out.println("Invalid choice, try again!\n");

         }

         }while (priority < 1 || priority > 4);


         // Source
         System.out.println("Select the source: \n");
         System.out.println("1 - USER_REPORT");
         System.out.println("2 - INTERNAL_TEAM");

         int source;
         IncidentSource selectedSource = null;

         do {

             try {
                 source = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number.");
                 source = 0;
             }

             // TODO: same thing as above
             switch (source){
                 case 1:
                     selectedSource = IncidentSource.USER_REPORT;
                     break;
                 case 2:
                     selectedSource = IncidentSource.INTERNAL_TEAM;
                     break;
                 default:
                     System.out.println("Invalid source, try again \n");
             }
         }while (source <1 || source > 2);


         Incident created = service.createIncident(title, selectedPriority , selectedSource);

         System.out.println("Incident created Successfully -  ID " + created.getId());
     }

     private void listAllIncidents(){
         List<Incident> incidents = service.allIncidents();

         // TODO: why not use the .toString inside incident? or even better creating a displayservice for incident?
         for (Incident incident :
              incidents) {
             System.out.println(incident.toString());
          }
      }

      private void findIncidentById(){

         int typedInt;
          System.out.println("Search an incident");
          System.out.println("Please type an ID: ");

          try {
              typedInt = Integer.parseInt(scanner.nextLine());
          } catch (NumberFormatException e) {
              System.out.println("Invalid input. Please enter a number.");
              typedInt = -1;
          }

          Optional<Incident> incidents = service.getIncidentById(typedInt);

            // TODO: please keep an eye on duplicated stuff, we should avoid them to follow the DRY principle
          if (incidents.isPresent()){

              Incident incident = incidents.get();

              System.out.println("ID " + incident.getId() + " | " + incident.getTitle());
              System.out.println("Status: " + incident.getStatus() + " | Priority: " + incidents.get().getPriority() + " | Reported by: " + incident.getSource());
              System.out.println("Assigned to: " + incident.getAssignedTo() + " | Start date: " + incident.getStartDate() + " | End Date: " + incident.getEndDate());
              System.out.println("Description \n" + incident.getDescription());

          }else
              System.out.println("Incident not found");
      }


      private void deleteIncidentById(){

         int typedInt;


          System.out.println("Incident Deletion");
          System.out.println("Please type an ID ");

          try {
              typedInt = Integer.parseInt(scanner.nextLine());
          } catch (NumberFormatException e) {
              System.out.println("Invalid input. Please enter a number.");
              typedInt = -1;
          }

          Optional<Incident> incidents = service.getIncidentById(typedInt);

          if (incidents.isPresent()){

              Incident incident = incidents.get();
              System.out.println("Incident: " + "[" + incident.getId() + "] | " + incident.getTitle() + " | " + incident.getStatus());
              System.out.println("Are you sure you want to delete this incident? y/n");
              String deletionInput = scanner.nextLine();

              if(deletionInput.equals("y")){
                  service.deleteIncident(typedInt);
                  System.out.println("Incident " + incident.getId() + " Cancelled");
              }else
                  System.out.println("Deletion process cancelled");

            // TODO: use always parentheses, even for one line code (good practice)
          }else
              System.out.println("Incident does not exist");

      }

    public void updateIncident() {

        boolean skipUpdate = false; // guard flag used for close incident
        int updateChoiceInput;

        System.out.println("\nUpdate Incident Information");
        System.out.println("Please type an ID: ");
        int typedInt;

        try {
            typedInt = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            typedInt = -1;
        }

        Optional<Incident> incidents = service.getIncidentById(typedInt);

        if (incidents.isPresent()) {

            Incident incident = incidents.get();

            System.out.println("ID " + incident.getId() + " | " + incident.getTitle());
            System.out.println("Status: " + incident.getStatus() + " | Priority: " + incidents.get().getPriority() + " | Reported by: " + incident.getSource());
            System.out.println("Assigned to: " + incident.getAssignedTo() + " | Start date: " + incident.getStartDate() + " | End Date: " + incident.getEndDate());
            System.out.println("Description \n" + incident.getDescription());


            do {
            // Start Update Information

            System.out.println("\nWhich information do you want to update?");
            System.out.println("1 - Title");
            System.out.println("2 - Description");
            System.out.println("3 - Status");
            System.out.println("4 - Priority");
            System.out.println("5 - Source");
            System.out.println("6 - Assigned");
            System.out.println("7 - Close incident");
            System.out.println("8 - Exit");

                try {
                    updateChoiceInput = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    updateChoiceInput = -1;
                }

            switch (updateChoiceInput){

                // UPDATE TITLE
                case 1:

                    String newTitle;
                    do {
                        System.out.println("Update title:");
                        newTitle = scanner.nextLine().trim();
                        if (newTitle.isBlank()) {
                            System.out.println("Title cannot be empty. Try again.");
                        }
                    } while (newTitle.isBlank());
                    incident.setTitle(newTitle);

                    break;

                // UPDATE  DESCRIPTION
                case 2:

                    System.out.println("Update description:");
                    String newDescription = scanner.nextLine();
                    incident.setDescription(newDescription);

                    break;

                // UPDATE STATUS
                case 3:

                    System.out.println("Update status:");
                    System.out.println("1 - ASSIGNED");
                    System.out.println("2 - ESCALATED");
                    System.out.println("3 - IN PROGRESS");
                    System.out.println("4 - TESTING");
                    System.out.println("5 - REOPENED");
                    System.out.println("6 - Exit");

                    int status;
                    IncidentStatus selectedNewStatus = null;

                    do {

                        try {
                            status = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            status = 0;
                        }

                        switch (status){
                            case 1:
                                selectedNewStatus = IncidentStatus.ASSIGNED;

                                // TODO: this kind of stuff, are NOT related to display element, this is business logic
                                if (incident.getAssignedTo() == null || incident.getAssignedTo().isEmpty()) {

                                    System.out.println("PLEASE CHANGE ASSIGNED PERSON!");
                                }

                                break;
                            case 2:
                                selectedNewStatus = IncidentStatus.ESCALATED;
                                break;
                            case 3:
                                selectedNewStatus = IncidentStatus.IN_PROGRESS;
                                break;
                            case 4:
                                selectedNewStatus = IncidentStatus.TESTING;
                                break;
                            case 5:
                                selectedNewStatus = IncidentStatus.REOPENED;
                                break;
                            case 6:
                                break;
                            default:
                                System.out.println("Invalid choice, try again!\n");

                        }

                    }while (status < 1 || status > 6);

                    incident.setStatus(selectedNewStatus);

                    break;

                // UPDATE PRIORITY
                case 4:

                    System.out.println("Update priority:");
                    System.out.println("1 -LOW");
                    System.out.println("2 -MEDIUM");
                    System.out.println("3 -HIGH");
                    System.out.println("4 -CRITICAL");

                    int priority;
                    Priority selectedNewPriority = null;

                    do {
                            try {
                                priority = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                                priority = 0;
                            }


                        switch (priority){
                            case 1:
                                selectedNewPriority = Priority.LOW;
                                break;
                            case 2:
                                selectedNewPriority = Priority.MEDIUM;
                                break;
                            case 3:
                                selectedNewPriority = Priority.HIGH;
                                break;
                            case 4:
                                selectedNewPriority = Priority.CRITICAL;
                                break;
                            default:
                                System.out.println("Invalid choice, try again!\n");

                        }

                    }while (priority < 1 || priority > 4);

                    incident.setPriority(selectedNewPriority);

                    break;

                    // UPDATE SOURCE
                case 5:

                    System.out.println("Update source: \n");
                    System.out.println("1 - USER_REPORT");
                    System.out.println("2 - INTERNAL_TEAM");

                    int source;
                    IncidentSource selectedNewSource = null;

                    do {

                        try {
                            source = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            source = 0;
                        }

                        switch (source){
                            case 1:
                                selectedNewSource = IncidentSource.USER_REPORT;
                                break;
                            case 2:
                                selectedNewSource = IncidentSource.INTERNAL_TEAM;
                                break;
                            default:
                                System.out.println("Invalid source, try again \n");
                        }
                    }while (source < 1 || source > 2);

                    incident.setSource(selectedNewSource);

                    break;

                    // UPDATE ASSIGNED
                case 6:

                    System.out.println("Update assigned person:");
                    String newAssigned = scanner.nextLine();
                    incident.setAssignedTo(newAssigned);

                    break;

                // CLOSE
                case 7:

                    System.out.println("Are you sure you want to close the incident? y/n");
                    String confirmClosureChoice = scanner.nextLine();

                    if(confirmClosureChoice.equals("y")) {
                        try {
                            service.closeIncident(typedInt);
                            System.out.println("Incident closed successfully!");
                            skipUpdate = true;
                            updateChoiceInput = 8;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }else
                        System.out.println("Incident closure cancelled");

                    break;

                    // EXIT
                case 8:

                    break;

                default:
                    System.out.println("Invalid choice try again!");


            }

            if(!skipUpdate)
            service.updateIncident(incident);

            }while (updateChoiceInput !=8 );


        }else

            System.out.println("No incident found");
    }

    public void checkSlaStatus() {
        Map<SlaStatus, List<Incident>> slaList = service.checkSlaStatus();
        List<Incident> okList = slaList.get(SlaStatus.OK);
        List<Incident> riskList = slaList.get(SlaStatus.AT_RISK);
        List<Incident> breachList = slaList.get(SlaStatus.BREACH);

        System.out.println("Report SLA status");
        System.out.println("─────────────────────────────");
        System.out.println("✅ OK STATUS: ");
        if (okList.isEmpty()) {
            System.out.println("No incidents in OK status");
        } else {
            for (Incident incident : okList) {

                long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());
                System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | " + incident.getPriority() + " | " + "expire in " + hoursLeft + " hours");
            }
        }

        System.out.println("─────────────────────────────");
        System.out.println("⚠️ RISK STATUS: ");

        if (riskList.isEmpty()) {

            System.out.println("No incidents at Risk status");

        } else
            for (Incident incident : riskList) {

                long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());

                System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | " + incident.getPriority() + " | " + "expire in " + hoursLeft + " hours");

            }

        System.out.println("─────────────────────────────");
        System.out.println("\uD83D\uDD34 BREACH STATUS: ");

        if (breachList.isEmpty()) {
            System.out.println("No breach ");
        } else

            for (Incident incident : breachList) {

                long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());

                System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | overdue since " + Math.abs(hoursLeft) + " hours");
            }
     }


}
