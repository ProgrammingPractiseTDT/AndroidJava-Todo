package com.example.finalproject;

public class Project {
    private String projectName;
    private  Project(){

    }
    public Project(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
