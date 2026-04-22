package com.ims.cli;

import com.ims.service.ReportService;

import java.util.Scanner;

public class ReportCLI {

    private Scanner scanner = new Scanner(System.in);
    private ReportService report = new ReportService();

    public void start(){
        System.out.println("IMS Reports");
        showReportMenu();

    }

    private void showReportMenu(){

        int input;

        do {
            System.out.println("\n1 - View report by Status");
            System.out.println("2 - View report by Priority");
            System.out.println("3 - View report by selected timeframe");
            System.out.println("4 - View report of resolution time");
            System.out.println("0 - Exit");

            System.out.println("Choose an option: ");

            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                input = 0;
            }

            switch (input){

                case 1:
                    System.out.println("Report By status coming soon..");
                    break;

                case 2:
                    System.out.println("Report By priority coming soon..");
                    break;

                case 3:
                    System.out.println("Report By average coming soon..");
                    break;

                case 4:
                    System.out.println("Report By resolution time coming soon..");
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Try again.\n");
            }

        }while (input !=0 );


    }
}
