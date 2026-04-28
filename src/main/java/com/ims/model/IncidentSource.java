package com.ims.model;

public enum IncidentSource {

    USER_REPORT(1, "User Report"),
    INTERNAL_TEAM(2, "Internal Team");

    private final int menuOrder;
    private final String displayText;

    IncidentSource(int menuOrder, String displayText){

        this.menuOrder=menuOrder;
        this.displayText=displayText;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public String getDisplayText() {
        return displayText;
    }
}
