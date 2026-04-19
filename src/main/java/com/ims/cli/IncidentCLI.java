package com.ims.cli;

import com.ims.model.Incident;
import com.ims.model.IncidentSource;
import com.ims.model.Priority;
import com.ims.service.IncidentService;

import java.util.Scanner;

public class IncidentCLI {
     private Scanner scanner = new Scanner(System.in);
     private IncidentService service = new IncidentService();

     public void start(){
         System.out.println("Welcome to the Incident Management System");
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
                     System.out.println("Coming soon..2");
                     break;

                 case 3:
                     System.out.println("Coming soon..3");
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

}
