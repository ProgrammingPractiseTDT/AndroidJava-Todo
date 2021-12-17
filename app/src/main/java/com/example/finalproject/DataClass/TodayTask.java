package com.example.finalproject.DataClass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.MultiProjectTaskAdapter;
import com.example.finalproject.Adapter.TaskAdapter;
import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;

import java.util.ArrayList;

public class TodayTask extends AppCompatActivity {
    public ArrayList<Task> tasks;
    public ArrayList<String> taskKeys;
    public ArrayList<Task> quickTasks;
    public ArrayList<String> quickTaskKeys;
    public ArrayList<String> projectKeys;
    public RecyclerView rv;
    public RecyclerView rv2;
    public FirebaseOperator firebaseOperator;
    public MultiProjectTaskAdapter multiProjectTaskAdapter;
    public TaskAdapter taskAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_task_layout);
        tasks = new ArrayList<>();
        taskKeys = new ArrayList<>();
        projectKeys = new ArrayList<>();


        multiProjectTaskAdapter = new MultiProjectTaskAdapter(TodayTask.this, tasks, taskKeys, projectKeys);
        rv = findViewById(R.id.recView_todayTask);
        rv2 = findViewById(R.id.recView_todayTask2);

        firebaseOperator = new FirebaseOperator();

        quickTasks = new ArrayList<>();
        quickTaskKeys = new ArrayList<>();
        taskAdapter = new TaskAdapter(TodayTask.this, quickTasks,quickTaskKeys, "QuickTasks" );
        firebaseOperator.todayTasksFiller(multiProjectTaskAdapter,tasks, taskKeys, projectKeys,
                taskAdapter, quickTasks, quickTaskKeys);

        rv.setAdapter(taskAdapter);
        rv2.setAdapter(multiProjectTaskAdapter);



    }

}
