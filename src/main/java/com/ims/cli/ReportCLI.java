package com.ims.cli;

import com.ims.model.Incident;
import com.ims.service.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// TODO: in general, same stuff as the IncidentCLI
public class ReportCLI {

    private final Scanner scanner = new Scanner(System.in);
    private final ReportService report = new ReportService();

    public void start(){
        System.out.println("IMS Reports");
        showReportMenu();

    }

    private void showReportMenu(){

        int input;

        do {
            System.out.println("\n1 - View report by Status");
            System.out.println("2 - View report by Priority");
            System.out.println("3 - View report of resolution time");
            System.out.println("4 - View report by selected timeframe");
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
                    printReportByStatus();
                    break;

                case 2:
                    printReportByPriority();
                    break;

                case 3:
                    printAverageResolutionTime();
                    break;

                case 4:
                    printIncidentByDateRange();
                    break;

                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Try again.\n");
            }

        }while (input !=0 );


    }

    public void printReportByStatus(){

        Map<String, Integer> statusMapReport = report.getStatusReport();

        System.out.println("=== INCIDENTS BY STATUS ===");

        for (Map.Entry<String, Integer> entry : statusMapReport.entrySet()){

            System.out.println( entry.getKey() + " : " + entry.getValue() );
        }
    }

    public void printReportByPriority() {

        Map<String, Integer> priorityMapReport = report.getPriorityReport();

        System.out.println("=== INCIDENTS BY PRIORITY ===");

        for (Map.Entry<String, Integer> entry : priorityMapReport.entrySet()) {

            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void printAverageResolutionTime(){

        float averageResolutionTime = report.getAverageResolutionTime();

        System.out.println("Average resolution time : " + averageResolutionTime + " hours");
    }

    public void printIncidentByDateRange(){

        System.out.println("Please select the timestamp - format YYYY-MM-DD");

        LocalDate fromDate = null;
        do {
            System.out.println("FROM (format YYYY-MM-DD): ");
            try {
                fromDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use YYYY-MM-DD. Try again.");
            }
        } while (fromDate == null);


        LocalDate toDate = null;

        do {
            System.out.println("TO (format YYYY-MM-DD): ");
            try {
                toDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use YYYY-MM-DD. Try again.");
            }
        } while (toDate == null);

        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atTime(23, 59, 59);

        try {

            List<Incident> listIncidentByRange = report.getIncidentByDateRange(from, to);

        if (listIncidentByRange.isEmpty()){
            System.out.println("No incident in this timeframe");
        }

        for (Incident incident : listIncidentByRange){


            System.out.println("[" + incident.getId() + "] " + incident.getTitle() + " | " + incident.getStatus() +
                    " | " + incident.getPriority() + " | " + incident.getStartDate() + " | " + incident.getEndDate());
        }
        }catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


}
