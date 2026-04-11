package com.ims.model;

import java.time.LocalDateTime;

public class Incident {

    /*
    id          → int
title       → String
description → String
status      → IncidentStatus
priority    → Priority
source      → IncidentSource
assignedTo  → String
startDate   → LocalDateTime
endDate     → LocalDateTime
     */

    private int id;
    private String title;
    private String description;
    private IncidentStatus status;
    private Priority priority;
    private IncidentSource source;
    private String assignedTo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    // CONSTRUCTOR
    public Incident(String title, Priority priority, IncidentSource source) {
        this.title = title;
        this.status = IncidentStatus.NEW;
        this.priority = priority;
        this.source = source;
        this.startDate = LocalDateTime.now();

    }

    // GETTER

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IncidentSource getSource() {
        return source;
    }

    public Priority getPriority() {
        return priority;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public int getId() {
        return id;
    }

    // SETTER


    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setSource(IncidentSource source) {
        this.source = source;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // OVERRIDE toString

    @Override
    public String toString() {
        return "[" + id + "] " + title +
                " | Status: " + status +
                " | Priority: " + priority +
                " | Assigned: " + assignedTo;
    }
}



