package com.example.finalproject;

public class Task {
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String folder;
    private int priority;

    public Task(String title, String description, String startTime, String endTime, String folder, int priority) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.folder = folder;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getFolder() {
        return folder;
    }

    public int getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
