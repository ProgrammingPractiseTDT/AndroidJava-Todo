package com.example.finalproject;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalproject.Adapter.MultiProjectTaskAdapter;
import com.example.finalproject.Adapter.TaskAdapter;
import com.example.finalproject.DataClass.QuickTask;
import com.example.finalproject.DataClass.Task;
import com.example.finalproject.DataClass.Project;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FirebaseOperator {
    private FirebaseUser user;
    private Project project;
    private  String result;

    public FirebaseOperator(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    public void searchAutoFiller(String keyword, MultiProjectTaskAdapter multiProjectTaskAdapter, ArrayList<Task> tasks, ArrayList<String> taskKeys, ArrayList<String> projectKeys,
                                 TaskAdapter taskAdapter, ArrayList<Task> quickTasks, ArrayList<String> quickTaskKeys){

//        Log.d("now", keyword);


            tasks.clear();
            quickTasks.clear();
            multiProjectTaskAdapter.notifyDataSetChanged();
            taskAdapter.notifyDataSetChanged();


        if(keyword.length()>0) {

            DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
            String finalKeyword = keyword.toLowerCase();
            projectsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tasks.clear();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        for (DataSnapshot dsp2 : dsp.child("tasks").getChildren()) {
                            Task task = dsp2.getValue(Task.class);
                            String title = task.getTitle().toLowerCase();
                            String description = task.getDescription().toLowerCase();
                            if (title.contains(finalKeyword) || description.contains(finalKeyword) ) {
                                tasks.add(task);
                                taskKeys.add(dsp2.getKey());
                                projectKeys.add(dsp.getKey());
                            }
                        }
                    }
                    multiProjectTaskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });


            //get from quick tasks
            DatabaseReference projectsRef2 = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("QuickTasks");
            projectsRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    quickTasks.clear();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        Task task = dsp.getValue(Task.class);
                        String title = task.getTitle().toLowerCase();
                        String description = task.getDescription().toLowerCase();
                        if (title.contains(finalKeyword) || description.contains(finalKeyword)) {
                            quickTasks.add(task);
                            quickTaskKeys.add(dsp.getKey());
                        }
                    }
                    taskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    public void QuickTasksFiller(TaskAdapter taskAdapter, ArrayList<Task> tasks, ArrayList<String> taskKeys) {
        DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("QuickTasks");
        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Task task = dsp.getValue(Task.class);
                        tasks.add(task);
                        taskKeys.add(dsp.getKey());
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void ImportantTasksFiller(MultiProjectTaskAdapter multiProjectTaskAdapter, ArrayList<Task> tasks, ArrayList<String> taskKeys, ArrayList<String> projectKeys) {
        DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    for (DataSnapshot dsp2 : dsp.child("tasks").getChildren()) {
                        Task task = dsp2.getValue(Task.class);
                        if(task.getPriority() == 3) {
                            tasks.add(task);
                            taskKeys.add(dsp2.getKey());
                            projectKeys.add(dsp.getKey());
                        }
                    }
                }
                multiProjectTaskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public boolean todayTasksFiller(MultiProjectTaskAdapter multiProjectTaskAdapter, ArrayList<Task> tasks, ArrayList<String> taskKeys, ArrayList<String> projectKeys,
                                    TaskAdapter taskAdapter, ArrayList<Task> quickTasks, ArrayList<String> quickTaskKeys){
        Calendar nowDate = Calendar.getInstance();
        String toDayString = Integer.toString(nowDate.get(5))+"-"+Integer.toString(nowDate.get(2)+1)+"-"+Integer.toString(nowDate.get(1));
        Log.d("now", toDayString);
        DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsp2 : dsp.child("tasks").getChildren()) {
                        Task task = dsp2.getValue(Task.class);
                        if(task.getEndTime().equals(toDayString)) {
                            tasks.add(task);
                            taskKeys.add(dsp2.getKey());
                            projectKeys.add(dsp.getKey());
                        }
                    }
                }
                multiProjectTaskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //get from quick tasks
        DatabaseReference projectsRef2 = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("QuickTasks");
        projectsRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quickTasks.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Task task = dsp.getValue(Task.class);
                    if(task.getEndTime().equals(toDayString)) {
                        quickTasks.add(task);
                        quickTaskKeys.add(dsp.getKey());
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return true;
    }


    public boolean deleteProjectByProjectKey(String projectKey) {
        DatabaseReference projectref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectref.removeValue();
        return true;
    }


    public boolean updateProject(String projectKey, String newTitle, int newColorID){
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
        projectRef.child("projectName").setValue(newTitle);
        projectRef.child("colorID").setValue(newColorID);
        return true;
    }
    public boolean deleteTasks(String projectKey, String tasksKey){
        if(projectKey.equals("QuickTasks")){
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                    child(user.getUid()).child("QuickTasks").child(tasksKey);
            taskRef.removeValue();
                    return true;
        }
        else {
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                    child(user.getUid()).child("projects").child(projectKey).child("tasks").child(tasksKey);
            taskRef.removeValue();
            return true;
        }
    }

    public boolean updateTasks(String projectKey, String tasksKey, boolean checkBox){
        if(projectKey.equals("QuickTasks")){
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                    child(user.getUid()).child("QuickTasks").child(tasksKey);
            taskRef.child("checkingStatus").setValue(checkBox);
            return true;
        }
        else {
            DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference().child("User").
                    child(user.getUid()).child("projects").child(projectKey).child("tasks").child(tasksKey);
            taskRef.child("checkingStatus").setValue(checkBox);
            return true;
        }
    }

//    boolean updateTask(String)

//    Project getProjectByKey(String projectKey){
//        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects").child(projectKey);
//
//    }

    public boolean autofillProjectEditor(String projectKey, EditText et, ConstraintLayout cl){
        result = "";
        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid())
                .child("projects").child(projectKey).child("projectName");
        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                result += snapshot.getValue();
                et.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference colorRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid())
                .child("projects").child(projectKey).child("colorID");
        colorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long result = (Long) snapshot.getValue();
                cl.setBackgroundColor(result.intValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return true;

    }


    public String getQuickTasksProjectKey() {
        final String[] output = {""};
        DatabaseReference projectsRef = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        projectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Project project = dsp.getValue(Project.class);
                    if(project.getProjectName().equals("QuickTask")){
                        output[0] = project.getProjectName();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return  output[0];
    }


}
