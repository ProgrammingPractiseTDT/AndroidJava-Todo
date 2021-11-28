package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    private EditText txtFullName, txtEmail, txtPassword, txtCFPassword;
    private Button btnSignUp, btnGetBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        txtFullName = (EditText) findViewById(R.id.txt_fullName);
        txtEmail = (EditText) findViewById(R.id.txt_signUpemail);
        txtPassword = (EditText) findViewById(R.id.txt_password);
        txtCFPassword = (EditText) findViewById(R.id.txt_cfPassword);

        btnSignUp = (Button) findViewById(R.id.btn_signUp);
        btnGetBack = (Button) findViewById(R.id.btn_backSignIn);

        btnGetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBacktoSignIn();
            }
        });
    }

    private void getBacktoSignIn() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}