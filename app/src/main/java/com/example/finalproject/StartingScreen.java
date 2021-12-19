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

        setStartLocale(StartingScreen.this);
        setContentView(R.layout.starting_screen);

        Paper.init(this);
        
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

        String syncUserEmail = Paper.book().read(SyncingUser.userEmail);
        String syncUserPassword = Paper.book().read(SyncingUser.userPassword);

        if(syncUserEmail != "" && syncUserPassword != ""){
            if(!TextUtils.isEmpty(syncUserEmail) && !TextUtils.isEmpty(syncUserPassword)){
                AllowAccess(syncUserEmail, syncUserPassword);
            }
        }
    }

    private void AllowAccess(final String syncUserEmail, final String syncUserPassword) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(syncUserEmail, syncUserPassword)
                .addOnCompleteListener(StartingScreen.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(StartingScreen.this, "Authentication failed.\n" + syncUserEmail + "\n"+ syncUserPassword + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser account){
        Intent intent = new Intent(this, HomeScreen.class);
        if(account != null){
            Toast.makeText(this,getString(R.string.signInSuccessful),Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else {
            Toast.makeText(this,"Invalid email or password.",Toast.LENGTH_LONG).show();
        }

    }
    public void setStartLocale(Activity activity){
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("Language","");
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}
