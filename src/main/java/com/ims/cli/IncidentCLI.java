package com.ims.cli;

import com.ims.model.*;
import com.ims.service.IncidentService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class IncidentCLI {
     private final Scanner scanner = new Scanner(System.in); // TODO: avoid using/creating Scanner for both ReportCLI and IncidentCLI

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

        // Select priority
         Priority selectedPriority  = askForPriority();

         // Select Source
         IncidentSource selectedSource = askSource();


         Incident created = service.createIncident(title, selectedPriority , selectedSource);

         System.out.println("Incident created Successfully -  ID " + created.getId());
     }

     private void listAllIncidents(){
         List<Incident> incidents = service.allIncidents();

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

          if (incidents.isPresent()){

              Incident incident = incidents.get();

              System.out.println("ID " + incident.getId() + " | " + incident.getTitle());
              System.out.println("Status: " + incident.getStatus() + " | Priority: " + incidents.get().getPriority() + " | Reported by: " + incident.getSource());
              System.out.println("Assigned to: " + incident.getAssignedTo() + " | Start date: " + incident.getStartDate() + " | End Date: " + incident.getEndDate());
              System.out.println("Description \n" + incident.getDescription());

          }else{

              System.out.println("Incident not found");

          }

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
              }else{

                  System.out.println("Deletion process cancelled");
              }

          }else{

              System.out.println("Incident does not exist");

          }

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

                    IncidentStatus selectedStatus = askUpdateStatus();

                    if (selectedStatus != null) {
                        try {
                            incident.setStatus(selectedStatus);
                        } catch (IllegalStateException e) {
                            System.out.println("Error: " + e.getMessage());
                            skipUpdate = true;
                        }
                    } else {
                        System.out.println("Status update cancelled");
                        skipUpdate = true;
                    }

                    break;

                // UPDATE PRIORITY
                case 4:

                    Priority selectedNewPriority = askForPriority();
                    incident.setPriority(selectedNewPriority);

                    break;

                    // UPDATE SOURCE
                case 5:

                    IncidentSource selectedNewSource = askSource();
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
                            skipUpdate = true;
                        }
                    }else{

                        System.out.println("Incident closure cancelled");

                    }


                    break;

                    // EXIT
                case 8:

                    break;

                default:
                    System.out.println("Invalid choice try again!");


            }

            if(!skipUpdate){

            service.updateIncident(incident);
            }

            }while (updateChoiceInput !=8 );

        }else{

            System.out.println("No incident found");

        }

    }

    public Priority askForPriority(){

        System.out.println("Select priority:");

        Priority[] priorities = Priority.values();

       // Loop for priority options
        for (Priority priority : priorities) {

            System.out.println(priority.getMenuOrder() + " - " + priority.getDisplayName());

        }



        int choice = 0;
        Priority selectedPriority = null;

        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());

            }catch (NumberFormatException e){
                System.out.println("Invalid input. Please try again");

            }
            if (choice >= 1 && choice <= priorities.length){
                selectedPriority = priorities[choice-1];
            }else{
                System.out.println("Invalid choice, try again");
            }

        } while (selectedPriority == null);

            return selectedPriority;

    }

    public IncidentStatus askUpdateStatus(){

        System.out.println("Update status:");

        IncidentStatus[] statusList = {
                IncidentStatus.ASSIGNED,
                IncidentStatus.ESCALATED,
                IncidentStatus.IN_PROGRESS,
                IncidentStatus.TESTING,
                IncidentStatus.REOPENED
        };

        for(int i = 0; i < statusList.length; i++){

            System.out.println((i+1) + " - " + statusList[i].getDisplayName());

        }
        System.out.println("6 - Exit");

        int choice = 0;
        IncidentStatus selectedStatus = null;

        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());

            }catch (NumberFormatException e){

                System.out.println("Invalid input. Please try again!");
            }
               if (choice == 6){
                   return null;

               } else if(choice >= 1 && choice <= 5) {

                   selectedStatus = statusList[choice - 1];

               }else {
                   System.out.println("Invalid choice, try again");
               }

        } while (selectedStatus == null);

        return selectedStatus;

    }

    public IncidentSource askSource(){

        System.out.println("Select priority:");

        IncidentSource[] sources = IncidentSource.values();

        for(IncidentSource source : sources){
            System.out.println(source.getMenuOrder() + " - " + source.getDisplayText());

        }

        int choice = 0;
        IncidentSource selectedSource = null;

        do {
            try {
                choice = Integer.parseInt(scanner.nextLine());

            }catch (NumberFormatException e){
                System.out.println("Invalid input. Please try again");

            }
            if (choice >= 1 && choice <= sources.length){
                selectedSource = sources[choice-1];
            }else{
                System.out.println("Invalid choice, try again");
            }

        } while (selectedSource == null);

        return selectedSource;

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

        } else{

            for (Incident incident : riskList) {

                long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());

                System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | " + incident.getPriority() + " | " + "expire in " + hoursLeft + " hours");

            }


        }


        System.out.println("─────────────────────────────");
        System.out.println("\uD83D\uDD34 BREACH STATUS: ");

        if (breachList.isEmpty()) {
            System.out.println("No breach ");
        } else {

            for (Incident incident : breachList) {

                long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), incident.getSlaDeadline());

                System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | overdue since " + Math.abs(hoursLeft) + " hours");
            }

        }


     }

}
