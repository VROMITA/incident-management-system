package com.ims;

import com.ims.cli.IncidentCLI;
import com.ims.repository.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();
        db.createTable();

        IncidentCLI cli = new IncidentCLI();
        cli.start();
    }
}