package edu.neu.csye7374.model;

import java.time.LocalDateTime;

public class ActivityLog {

    private int logId;
    private String activityAction;
    private LocalDateTime activityTimestamp;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getActivityAction() {
        return activityAction;
    }

    public void setActivityAction(String activityAction) {
        this.activityAction = activityAction;
    }

    public LocalDateTime getActivityTimestamp() {
        return activityTimestamp;
    }

    public void setActivityTimestamp(LocalDateTime activityTimestamp) {
        this.activityTimestamp = activityTimestamp;
    }

    @Override
    public String toString() {
        return String.format("ActivityLog [logId=%d, activityAction='%s', activityTimestamp=%s]", logId, activityAction, activityTimestamp);
    }
}
