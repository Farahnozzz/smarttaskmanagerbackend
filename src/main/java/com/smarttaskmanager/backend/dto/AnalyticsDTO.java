package com.smarttaskmanager.backend.dto;

public class AnalyticsDTO {
    private long totalTasks;
    private long toDo;
    private long inProgress;
    private long done;

    public AnalyticsDTO() {
    }

    public AnalyticsDTO(long totalTasks, long toDo, long inProgress, long done) {
        this.totalTasks = totalTasks;
        this.toDo = toDo;
        this.inProgress = inProgress;
        this.done = done;
    }

    // Getters and Setters
    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getToDo() {
        return toDo;
    }

    public void setToDo(long toDo) {
        this.toDo = toDo;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getDone() {
        return done;
    }

    public void setDone(long done) {
        this.done = done;
    }
}