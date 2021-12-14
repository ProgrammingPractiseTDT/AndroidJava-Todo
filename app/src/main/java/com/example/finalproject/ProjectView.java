package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProjectView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);
        String ProjectTitle;
        ProjectTitle =getIntent().getExtras().getString("Project Title");
        TextView tv = findViewById(R.id.txt_projectName);
        tv.setText(ProjectTitle);
    }
}