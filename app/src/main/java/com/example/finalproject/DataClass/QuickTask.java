package com.example.finalproject.DataClass;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.MultiProjectTaskAdapter;
import com.example.finalproject.Adapter.TaskAdapter;
import com.example.finalproject.Dialog.AddTaskDialog;
import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;

import java.util.ArrayList;

public class QuickTask extends AppCompatActivity {


    public ArrayList<Task> tasks;
    public ArrayList<String> taskKeys;
    public String projectKey;
    public RecyclerView rv;
    public FirebaseOperator firebaseOperator;
    private  TaskAdapter taskAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_task_layout);
        tasks = new ArrayList<>();
        taskKeys = new ArrayList<>();
        firebaseOperator = new FirebaseOperator();

        taskAdapter = new TaskAdapter(QuickTask.this, tasks, taskKeys, "QuickTasks");
        rv = findViewById(R.id.recView_quickTask);


        firebaseOperator.QuickTasksFiller(taskAdapter,tasks, taskKeys);
        rv.setAdapter(taskAdapter);
    }

    public void addQuickTask(View view) {
        AddTaskDialog atd = new AddTaskDialog(QuickTask.this);
        atd.show();
    }
}
