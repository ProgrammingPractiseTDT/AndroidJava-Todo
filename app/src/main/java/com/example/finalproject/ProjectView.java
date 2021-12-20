package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.Adapter.TaskAdapter;
import com.example.finalproject.Dialog.AddTaskDialog;
import com.example.finalproject.DataClass.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;

public class ProjectView extends AppCompatActivity {
    AddTaskDialog atd;
    RecyclerView rv;
    TaskAdapter taskAdapter;
    ArrayList<Task> taskList;
    private FirebaseUser user;
    String ProjectKey;
    ArrayList<String> TasksKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);
        String ProjectTitle;
        ProjectTitle =getIntent().getExtras().getString("Project title");
        user = FirebaseAuth.getInstance().getCurrentUser();

        ProjectKey = getIntent().getExtras().getString("Project key");
        TextView tv = findViewById(R.id.txt_projectName);
        tv.setText(ProjectTitle);

        //firebase user variable
        TasksKey = new ArrayList<String>();
        //recycler view init
        rv = findViewById(R.id.task_recycler_view);
        taskList = new ArrayList<Task>();
        fetchTaskFromProject(ProjectKey);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL);
//        rv.addItemDecoration(dividerItemDecoration);

        taskAdapter = new TaskAdapter(this,taskList, TasksKey, ProjectKey);
        rv.setAdapter(taskAdapter);
        atd = new AddTaskDialog(ProjectView.this, ProjectKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchTaskFromProject(ProjectKey);
    }

    public void addTask(View view) {
        //execute when click add task button
        atd.show();
    }

//    public ArrayList<Task> sortingTask (ArrayList<Task> taskList){
//        ArrayList<Task> tasks = new ArrayList<Task>();
//        for (int i = 0; i < taskList.size(); i++){
//            if (taskList.get(i).isCheckingStatus() == false){
//                tasks.add(taskList.get(i));
//            }
//        }for (int i = 0; i < taskList.size(); i++){
//            if (taskList.get(i).isCheckingStatus() == true){
//                tasks.add(taskList.get(i));
//            }
//        }
//        return tasks;
//    }

    public void fetchTaskFromProject(String ProjectKey){
        Query reference =  FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(ProjectKey).child("tasks").orderByChild("checkingStatus");
        //Query query = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(ProjectKey).child("tasks");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                taskList.clear();
                TasksKey.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Task task = dsp.getValue(Task.class);
                    TasksKey.add(dsp.getKey());
                    taskList.add(task);
                }
//               for (int i = 0; i < taskList.size(); i++) {
//                   for (int j = 0; j < taskList.size() - i - 1; j++) {
//                       boolean a = taskList.get(j).getCheckingStatus();
//                       boolean b = taskList.get(j + 1).getCheckingStatus();
//                       if (a == true && b == false) {
//                           Task temp = taskList.get(j);
//                           taskList.set(j, taskList.get(j + 1));
//                           taskList.set(j + 1, temp);
//
//                           String tempkey = TasksKey.get(j);
//                           TasksKey.set(j, TasksKey.get(j + 1));
//                           TasksKey.set(j + 1, tempkey);
//                       }
//                   }
//               }
////        }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FetchError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addValueEventListener(postListener);
    }
}