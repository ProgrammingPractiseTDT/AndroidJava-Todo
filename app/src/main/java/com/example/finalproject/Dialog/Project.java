package com.example.finalproject.Dialog;

public class Project {
    private String projectName;
    private int colorID;

    public   Project(String projectName, int colorID){
        this.projectName = projectName;
        this.colorID = colorID;
    }
    public int getColorID(){
        return colorID;
    }
    public Project(String projectName) {
        this.projectName = projectName;
    }
    public Project(){

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setColorID(int colorID){
        this.colorID = colorID;
    }

}
