package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectView extends AppCompatActivity {
    AddTaskDialog atd;
    RecyclerView rv;
    TaskAdapter taskAdapter;
    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);
        String ProjectTitle;
        ProjectTitle =getIntent().getExtras().getString("Project Title");
        TextView tv = findViewById(R.id.txt_projectName);
        tv.setText(ProjectTitle);

        //recycler view init
        rv = findViewById(R.id.task_recycler_view);
        taskList = new ArrayList<Task>();
        taskList.add( new Task("Shopping"));
        taskList.add(new Task("Study"));
        taskAdapter = new TaskAdapter(this,taskList);
        rv.setAdapter(taskAdapter);
        atd = new AddTaskDialog(ProjectView.this);
    }

    public void addTask(View view) {
        //execute when click add task button
        atd.show();
    }
}