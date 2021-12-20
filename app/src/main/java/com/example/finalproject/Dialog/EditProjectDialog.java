package com.example.finalproject.Dialog;

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

import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProjectDialog extends Dialog implements
        android.view.View.OnClickListener {

    public android.content.Context c;
    public Dialog d;
    public Button yes, no;
    private  String projectKey;
    private Button red, yellow, green, orange;
    EditText title_field;
    FirebaseOperator firebaseOperator;

    public EditProjectDialog(Context a, String projectKey) {
        super(a);
        this.c = a;
        this.projectKey = projectKey;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseOperator = new FirebaseOperator();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_project_dialog);
        ConstraintLayout dialoglayout = (ConstraintLayout) findViewById(R.id.layout_dialog);
        red = findViewById(R.id.btn_redButton);
        yellow = findViewById(R.id.btn_yellowButton);
        orange = findViewById(R.id.btn_orangeButton);
        green = findViewById(R.id.btn_greenButton);
        yes = (Button) findViewById(R.id.add_dialog_btn);
        no = (Button) findViewById(R.id.cancel_dialog_btn);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(Color.parseColor("#EF5959"));
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoglayout.setBackgroundColor(c.getResources().getColor(R.color.light_blue));
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

        title_field = findViewById(R.id.project_title_input);
        String project;
        firebaseOperator.autofillProjectEditor(projectKey, title_field, dialoglayout);
//        title_field.setText(project);
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
                    firebaseOperator.updateProject(projectKey,title, colorId);
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

}