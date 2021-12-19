package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.Adapter.ProjectAdapter;
import com.example.finalproject.Dialog.AddProjectDialog;
import com.example.finalproject.DataClass.ImportantTask;
import com.example.finalproject.DataClass.Project;
import com.example.finalproject.DataClass.QuickTask;
import com.example.finalproject.DataClass.TodayTask;
import com.example.finalproject.DataClass.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;


import io.paperdb.Paper;


//test
public class HomeScreen extends AppCompatActivity{
    private TextView userGreeting;
    private FirebaseUser user;
    private RecyclerView rv;
    private Button todayBtn;
    private Button importantBtn;
    private Button quickTaskBtn;
    private ImageView searchBtn;
    private ImageView popupMenuBtn;
    private String initialLocale;
    ProjectAdapter projectAdapter;
    ArrayList<Project> ProjectNames;
    ArrayList<String> ProjectKeys;
    AddProjectDialog cdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_screen_layout);

        //Move to TodayTask, ImportantTask, QuickTask
        todayBtn = findViewById(R.id.btn_today);
        importantBtn = findViewById(R.id.btn_important);
        quickTaskBtn = findViewById(R.id.btn_quickTask);
        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, TodayTask.class));
            }
        });
        importantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, ImportantTask.class));
            }
        });
        quickTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, QuickTask.class));
            }
        });

        //search Activity
        searchBtn = findViewById(R.id.img_searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, SearchActivity.class));
            }
        });

        //Popup Menu
        popupMenuBtn = findViewById(R.id.img_menu);
        popupMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

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
                    userGreeting.setText(getString(R.string.hello)+user.getFullName()+"!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        ProjectNames = new ArrayList<Project>();
        ProjectKeys = new ArrayList<String>();
        getProjectFromUser();


        rv = findViewById(R.id.project_recycler_view);
        projectAdapter = new ProjectAdapter(HomeScreen.this, ProjectNames, ProjectKeys);
        rv.setAdapter(projectAdapter);

        //add project dialog
        cdd=new AddProjectDialog(HomeScreen.this);
        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getProjectFromUser();
                projectAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showAddProjectDialog(View view) {
        AddProjectDialog cdd=new AddProjectDialog(HomeScreen.this);
//        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                getProjectFromUser();
//                projectAdapter.notifyDataSetChanged();
//            }
//        });
        cdd.show();
    }

    void getProjectFromUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ProjectNames.clear();
                ProjectKeys.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Project project = dsp.getValue(Project.class);
                    ProjectKeys.add(dsp.getKey());
                    ProjectNames.add(project); //add result into array list
                }
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FetchError", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addValueEventListener(postListener);
    }



    void setProjectNames(ArrayList<Project> ProjectNames){
        this.ProjectNames = ProjectNames;
    }

    public void showPopup(View view){
        Context theme = new ContextThemeWrapper(this, R.style.appPopupMenu);
        PopupMenu popupMenu = new PopupMenu(theme, popupMenuBtn);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 switch (menuItem.getItemId()){
                     case R.id.userProfile:
                         Toast.makeText(HomeScreen.this, "UserProfile", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(HomeScreen.this, UserProfile.class);
                         startActivity(intent);
                         intent.putExtra("User id", user);
                         return true;
                     case R.id.settings:
                         startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                         return true;
                     case R.id.logoutBtn:
                         Paper.book().destroy();
                         startActivity(new Intent(HomeScreen.this, StartingScreen.class));
                         return true;
                     default:
                         return false;
             }
         }
        });
        try{
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for(Field field : fields){
                if("mPopup".equals(field.getName())){
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }

        } catch (Exception e) {

        }finally {
            popupMenu.show();
        }
    }

}