package com.example.finalproject.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProjectDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private Button red, yellow, green, blue;
    public AddProjectDialog(Activity a) {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_project_dialog);
        ConstraintLayout dialoglayout = (ConstraintLayout) findViewById(R.id.layout_dialog);
        red = findViewById(R.id.btn_redButton);
        yellow = findViewById(R.id.btn_yellowButton);
        blue = findViewById(R.id.btn_blueButton);
        green = findViewById(R.id.btn_greenButton);
        yes = (Button) findViewById(R.id.add_dialog_btn);
        no = (Button) findViewById(R.id.cancel_dialog_btn);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(Color.parseColor("#EF5959"));
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(Color.parseColor("#29ACF3"));
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(Color.parseColor("#8BC34A"));
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(Color.parseColor("#FFEB3B"));
            }
        });
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_dialog_btn:
                EditText title_field = findViewById(R.id.project_title_input);
                String title = title_field.getText().toString();
                ConstraintLayout lay = (ConstraintLayout) findViewById(R.id.layout_dialog);
                ColorDrawable viewColor = (ColorDrawable) lay.getBackground();
                int colorId;
                if (viewColor == null){
                    colorId = Color.parseColor("#FFFFFF");
                }
                else{
                    colorId = viewColor.getColor();
                }
                if (title.isEmpty()){
                    title_field.setError("Please enter project title");
                    title_field.requestFocus();
                }
                else{
                    addProject(title, colorId);
                    dismiss();
                }
                break;
            case R.id.cancel_dialog_btn:
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