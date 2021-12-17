package com.example.finalproject.DataClass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.MultiProjectTaskAdapter;
import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;

import java.util.ArrayList;

public class ImportantTask extends AppCompatActivity {

    public ArrayList<Task> tasks;
    public ArrayList<String> taskKeys;
    public ArrayList<String> projectKeys;
    public RecyclerView rv;
    public FirebaseOperator firebaseOperator;
    public MultiProjectTaskAdapter multiProjectTaskAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.important_task_layout);
        tasks = new ArrayList<>();
        taskKeys = new ArrayList<>();
        projectKeys = new ArrayList<>();


        multiProjectTaskAdapter = new MultiProjectTaskAdapter(ImportantTask.this, tasks, taskKeys, projectKeys);
        rv = findViewById(R.id.recView_importantTask);
        firebaseOperator = new FirebaseOperator();

        firebaseOperator.ImportantTasksFiller(multiProjectTaskAdapter,tasks, taskKeys, projectKeys);
        rv.setAdapter(multiProjectTaskAdapter);

    }
}
