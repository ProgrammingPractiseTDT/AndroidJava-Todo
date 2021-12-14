package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


//test
public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<String> menu;
    private TextView userGreeting;
    private FirebaseUser user;
    private RecyclerView rv;
    ProjectAdapter projectAdapter;
    ArrayList<String> ProjectNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);


        //User Greeting
        userGreeting = findViewById(R.id.txt_userGreeting);
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
        if(user!=null){
            String uid = user.getUid();

            reference.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    userGreeting.setText("Hello, "+user.getFullName()+"!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //list main menu
        ProjectNames = new ArrayList<String>();
        getProjectFromUser();
        menu = getMenuItem();
        lv = findViewById(R.id.listview_mainMenu);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(this, menu);
        lv.setAdapter(mainMenuAdapter);


        rv = findViewById(R.id.project_recycler_view);
        projectAdapter = new ProjectAdapter(MainActivity.this, ProjectNames);
        rv.setAdapter(projectAdapter);
    }

    private List<String> getMenuItem(){
        List<String> menu = new ArrayList<String>();
        menu.add("Today");
        menu.add("Important");
        menu.add("All tasks");

        return menu;
    }

    public void showAddProjectDialog(View view) {
        AddProjectDialog cdd=new AddProjectDialog(MainActivity.this);
        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getProjectFromUser();
                projectAdapter.notifyDataSetChanged();
            }
        });
        cdd.show();
    }

    void getProjectFromUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ProjectNames.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Project project = dsp.getValue(Project.class);
                    ProjectNames.add(project.getProjectName()); //add result into array list
                    projectAdapter.notifyDataSetChanged();
                }
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FetchError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addValueEventListener(postListener);
    }



    void setProjectNames(ArrayList<String> ProjectNames){
        this.ProjectNames = ProjectNames;
    }
}