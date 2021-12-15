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
}
