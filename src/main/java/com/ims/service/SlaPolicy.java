package com.ims.service;

public class SlaPolicy {

    public static final long CRITICAL = 4;
    public static final long HIGH = 12;
    public static final long MEDIUM = 24;
    public static final long LOW = 48;
    public static final long AT_RISK_THRESHOLD = 6;

    // TODO: this is the base class to improve to proceed with the refactor of SlaMonitor class

    /*
       // TODO: my suggestions:
    public static long getSlaHours(Priority priority) {
        return switch (priority) {
            case CRITICAL -> 4;
            case HIGH -> 12;
            case MEDIUM -> 24;
            case LOW -> 48;
        };
    }

    public static final long AT_RISK_THRESHOLD = 6;

      OR (better approach) add a value to the Priority enum for the the slaHours and delete completely this class

     */
}
