package com.ims.model;

public enum Priority {

    LOW(48, 1, "🟢 Low"),
    MEDIUM(24, 2, "🟡 Medium"),
    HIGH(12, 3, "🟠 High"),
    CRITICAL(4, 4, "🔴 Critical");

    private final long slaHours;
    private final int menuOrder;
    private final String displayName;
    public static final long AT_RISK_THRESHOLD = 6;

    Priority(long slaHours, int menuOrder, String displayName){
        this.slaHours=slaHours;
        this.menuOrder=menuOrder;
        this.displayName=displayName;
    }

    public long getSlaHours() {
        return slaHours;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public String getDisplayName() {
        return displayName;
    }
}
