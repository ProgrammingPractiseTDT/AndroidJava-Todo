package com.example.finalproject;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.MultiProjectTaskAdapter;
import com.example.finalproject.Adapter.TaskAdapter;
import com.example.finalproject.DataClass.Task;
import com.example.finalproject.DataClass.TodayTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
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
        setContentView(R.layout.search_screen_layout);


        //
        tasks = new ArrayList<>();
        taskKeys = new ArrayList<>();
        projectKeys = new ArrayList<>();


        multiProjectTaskAdapter = new MultiProjectTaskAdapter(SearchActivity.this, tasks, taskKeys, projectKeys);
        rv = findViewById(R.id.search_recyclerView2);
        rv2 = findViewById(R.id.search_recyclerView1);

        firebaseOperator = new FirebaseOperator();

        quickTasks = new ArrayList<>();
        quickTaskKeys = new ArrayList<>();
        taskAdapter = new TaskAdapter(SearchActivity.this, quickTasks,quickTaskKeys, "QuickTasks" );
        rv.setAdapter(taskAdapter);
        rv2.setAdapter(multiProjectTaskAdapter);


        //
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchProcess(s);
                return true;
            }
        });



    }

    public void searchProcess(String s){
//        String query = s.toLowerCase();
//        Query firebaseQuery = reference.orderByChild("search").startAt(query).endAt(query);
            firebaseOperator.searchAutoFiller(s, multiProjectTaskAdapter, tasks, taskKeys, projectKeys, taskAdapter, quickTasks, quickTaskKeys);
//            Log.d("search", s);

    }
}
