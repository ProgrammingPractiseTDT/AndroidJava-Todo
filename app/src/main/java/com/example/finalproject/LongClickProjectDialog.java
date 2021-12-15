package com.example.finalproject;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LongClickProjectDialog extends Dialog implements
        android.view.View.OnClickListener {

    public android.content.Context c;
    public Dialog d;
    public Button delete, edit;
    private String projectKey;
    public LongClickProjectDialog(Context a, String projectKey) {
        super(a);
        this.c = a;
        this.projectKey=projectKey;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.long_touch_option_dialog);


        delete = (Button) findViewById(R.id.delete_project_btn);
        edit = (Button) findViewById(R.id.edit_project_btn);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_project_btn:
                FirebaseOperator firebaseOperator = new FirebaseOperator();
                firebaseOperator.deleteProjectByProjectKey(projectKey);
                dismiss();
                break;
            case R.id.edit_project_btn:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void addProject(String title, int color) {


        Project project = new Project(title, color);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = "";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }
        DatabaseReference ref = rootRef.child("User").child(uid).child("projects");
        ref.push().setValue(project);
    }
}
