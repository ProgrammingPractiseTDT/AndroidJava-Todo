package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.Set;

import io.paperdb.Paper;

public class StartingScreen extends AppCompatActivity {

    private Button joinNowBtn;
    private Button alreadySignedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen);

        joinNowBtn = (Button) findViewById(R.id.btn_newbieUser);
        alreadySignedBtn = (Button) findViewById(R.id.btn_signedUser);

        joinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartingScreen.this, Signup.class));
            }
        });

        alreadySignedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartingScreen.this, Login.class));
            }
        });


    }






}
