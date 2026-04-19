package com.ims.cli;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
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
             System.out.println("1 - Create a new incident");
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
                     System.out.println("Coming soon..4");
                     break;

                 case 5:
                     System.out.println("Coming soon..5");
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




}
