package com.ims.cli;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
import com.ims.model.IncidentStatus;
import com.ims.model.Priority;
import com.ims.service.IncidentService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class IncidentCLI {
     private Scanner scanner = new Scanner(System.in);
     private IncidentService service = new IncidentService();

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
             System.out.println("6 - Exit");

             System.out.println("Choose an option: ");
             input = Integer.parseInt(scanner.nextLine());

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
                     break;

                 default:
                     System.out.println("Invalid option. Try again.\n");
             }

         }while (input !=6 );

         System.out.println("Bye Bye!\n");

     }

     private void createIncident(){

         System.out.println("Incident Creation\n");

         // Start fill out data

         // Title
         System.out.println("Choose a title: ");
         String title = scanner.nextLine();

         // Priority
         System.out.println("Select priority:");
         System.out.println("1 -LOW");
         System.out.println("2 -MEDIUM");
         System.out.println("3 -HIGH");
         System.out.println("4 -CRITICAL");

         int priority;
         Priority selectedPriority = null;

         do {

             priority = Integer.parseInt(scanner.nextLine());

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

         }while (priority < 1 || priority > 3);


         // Source
         System.out.println("Select the source: \n");
         System.out.println("1 - USER_REPORT");
         System.out.println("2 - INTERNAL_TEAM");

         int source;
         IncidentSource selectedSource = null;

         do {

             source = Integer.parseInt(scanner.nextLine());

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

         for (Incident incident :
              incidents) {
             System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | " + incident.getStatus() + " | " + incident.getPriority() + " | " + incident.getStartDate() + " | " + incident.getEndDate());
          }
      }

      private void findIncidentById(){

         int typedInt;
          System.out.println("Search an incident");
          System.out.println("Please type an ID: ");
          typedInt = Integer.parseInt(scanner.nextLine());

          Optional<Incident> incidents = service.getIncidentById(typedInt);


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
          typedInt = Integer.parseInt(scanner.nextLine());

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


          }else
              System.out.println("Incident does not exist");

      }

    public void updateIncident() {

        boolean skipUpdate = false; // guard flag used for close incident
        int updateChoiceInput;

        System.out.println("\nUpdate Incident Information");
        System.out.println("Please type an ID: ");
        int typedInt = Integer.parseInt(scanner.nextLine());

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

            updateChoiceInput = Integer.parseInt(scanner.nextLine());

            switch (updateChoiceInput){

                // UPDATE TITLE
                case 1:

                    System.out.println("Update title:");
                    String newTitle = scanner.nextLine();
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

                        status = Integer.parseInt(scanner.nextLine());

                        switch (status){
                            case 1:
                                selectedNewStatus = IncidentStatus.ASSIGNED;

                                if (incident.getAssignedTo() == null || incident.getAssignedTo().isEmpty())

                                System.out.println("PLEASE CHANGE ASSIGNED PERSON!");

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

                        priority = Integer.parseInt(scanner.nextLine());

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

                        source = Integer.parseInt(scanner.nextLine());

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

                // EXIT
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
                    }

                    break;

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

}
