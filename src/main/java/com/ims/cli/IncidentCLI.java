package com.ims.cli;

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
             input = scanner.nextInt();

             switch (input){

                 case 1:
                     System.out.println("Coming soon..1");
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
                     System.out.println("Invalid option. Try again.");
             }

         }while (input !=6 );

         System.out.println("Bye Bye!");

     }
}
