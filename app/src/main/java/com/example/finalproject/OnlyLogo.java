package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import io.paperdb.Paper;

public class OnlyLogo extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setStartLocale(OnlyLogo.this);
        setContentView(R.layout.only_logo_layout);
        View currentView = this.findViewById(android.R.id.content);

        Paper.init(this);
        String syncUserEmail = Paper.book().read(SyncingUser.userEmail);
        String syncUserPassword = Paper.book().read(SyncingUser.userPassword);

        if(syncUserEmail != "" && syncUserPassword != ""){
            if(!TextUtils.isEmpty(syncUserEmail) && !TextUtils.isEmpty(syncUserPassword)){
                AllowAccess(syncUserEmail, syncUserPassword);
            }
            else{
                canTouch(currentView);
            }
        }else {
            canTouch(currentView);
        }


        tv = findViewById(R.id.txt_letStart);
        Animation mAnimation = new AlphaAnimation(1, 0);
        mAnimation.setDuration(852);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.REVERSE); tv.startAnimation(mAnimation);



    }


    private void canTouch(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        startActivity(new Intent(OnlyLogo.this,StartingScreen.class));
                        break;
                }
                return true;
            }
        });
    }
    private void AllowAccess(final String syncUserEmail, final String syncUserPassword) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(syncUserEmail, syncUserPassword)
                .addOnCompleteListener(OnlyLogo.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(OnlyLogo.this, "Authentication failed.\n" + syncUserEmail + "\n"+ syncUserPassword + task.getException().getMessage(),
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
