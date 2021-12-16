package com.example.finalproject;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalproject.DataClass.Project;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseOperator {
    private FirebaseUser user;
    private Project project;
    private  String result;

    public FirebaseOperator(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    boolean deleteProjectByProjectKey(String projectKey) {
        DatabaseReference projectref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectref.removeValue();
        return true;
    }


    boolean updateProject(String projectKey, String newTitle, int newColorID){
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectRef.child("projectName").setValue(newTitle);
        projectRef.child("colorID").setValue(newColorID);
        return true;
    }
    public boolean deleteTasks(String projectKey, String tasksKey){
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                child(user.getUid()).child("projects").child(projectKey).child("tasks").child(tasksKey);
        taskRef.removeValue();
        return true;
    }

    public boolean updateTasks(String projectKey, String tasksKey, boolean checkBox){
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

    boolean autofillProjectEditor(String projectKey, EditText et, ConstraintLayout cl){
        result = "";
        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid())
                .child("projects").child(projectKey).child("projectName");
        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                result += snapshot.getValue();
                et.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference colorRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid())
                .child("projects").child(projectKey).child("colorID");
        colorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long result = (Long) snapshot.getValue();
                cl.setBackgroundColor(result.intValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;

    }
}
