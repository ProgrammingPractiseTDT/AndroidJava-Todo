package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Dialog.ChangeUsernameDialog;

public class UserProfile extends AppCompatActivity {
    Button changeUsername, changePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        changeUsername = (Button) findViewById(R.id.btn_changeUserName);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUsernameDialog dia = new ChangeUsernameDialog(UserProfile.this);
                dia.show();
            }
        });
    }
}
