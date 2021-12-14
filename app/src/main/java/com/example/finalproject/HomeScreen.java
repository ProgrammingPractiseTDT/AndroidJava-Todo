package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
public class HomeScreen extends AppCompatActivity {
    private ListView lv;
    private List<String> menu;
    private TextView userGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);


        //User Greeting
        userGreeting = findViewById(R.id.txt_userGreeting);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
        menu = getMenuItem();
        lv = findViewById(R.id.listview_mainMenu);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(this, menu);
        lv.setAdapter(mainMenuAdapter);


    }

    private List<String> getMenuItem(){
        List<String> menu = new ArrayList<String>();
        menu.add("Today");
        menu.add("Important");
        menu.add("All tasks");

        return menu;
    }
}