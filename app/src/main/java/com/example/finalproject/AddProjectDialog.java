package com.example.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProjectDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public AddProjectDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_project_dialog);
        yes = (Button) findViewById(R.id.add_dialog_btn);
        no = (Button) findViewById(R.id.cancel_dialog_btn);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_dialog_btn:
                addProject();
                dismiss();
                break;
            case R.id.cancel_dialog_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void addProject() {
        EditText title_field = findViewById(R.id.project_title_input);
        String title = title_field.getText().toString();
        Project project = new Project(title);
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