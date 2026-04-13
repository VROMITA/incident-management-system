package com.ims;

import com.ims.repository.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();
        db.createTable();
    }
}