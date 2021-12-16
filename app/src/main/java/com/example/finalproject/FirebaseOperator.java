package com.example.finalproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseOperator {
    private FirebaseUser user;

    public FirebaseOperator(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    boolean deleteProjectByProjectKey(String projectKey) {
        DatabaseReference projectref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectref.removeValue();
        return true;
    }


    boolean updateProject(String projectKey, String newTitle, String newColor){
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectRef.child("projectName").setValue(newTitle);
        return true;
    }
    boolean deleteTasks(String projectKey, String tasksKey){
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                child(user.getUid()).child("projects").child(projectKey).child("tasks").child(tasksKey);
        taskRef.removeValue();
        return true;
    }

    boolean updateTasks(String projectKey, String tasksKey, boolean checkBox){
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                child(user.getUid()).child("projects").child(projectKey).child("tasks").child(tasksKey);
        taskRef.child("checkingStatus").setValue(checkBox);
        return true;
    }

//    boolean updateTask(String)

//    Project getProjectByKey(String projectKey){
//        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
//
//    }
}
