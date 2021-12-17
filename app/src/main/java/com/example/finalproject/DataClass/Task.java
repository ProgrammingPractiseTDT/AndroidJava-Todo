package com.example.finalproject.DataClass;

public class Task {
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String onTime;
    private String folder;
    private int priority;
    private boolean checkingStatus;

//    public Task(String title){
//        this.title = title;
//        this.description = "";
//        this.startTime = "";
//        this.endTime = "";
//        this.folder = "";
//        this.priority = 4;
//    }
    public Task(String title, String endTime,String onTime, String description, int priority){
        this.title = title;
        this.description = description;
        this.endTime = endTime;
        this.onTime = onTime;
        this.folder = "";
        this.priority = priority;
        this.checkingStatus = false;
    }
//    public Task(String title, String description, String startTime, String endTime, String folder, int priority) {
//        this.title = title;
//        this.description = description;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.folder = folder;
//        this.priority = priority;
//    }
    public Task(){

    }

    public boolean isCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(boolean checkingStatus) {
        this.checkingStatus = checkingStatus;
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

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }
}
