package com.ims.model;

public enum IncidentStatus {

    NEW("New"),
    ASSIGNED("Assigned"),
    ESCALATED("Escalated"),
    IN_PROGRESS("In progress"),
    TESTING("Testing"),
    CLOSED("Closed"),
    REOPENED("Reopened");

    private final String displayName;

    IncidentStatus(String displayName){
        this.displayName=displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
