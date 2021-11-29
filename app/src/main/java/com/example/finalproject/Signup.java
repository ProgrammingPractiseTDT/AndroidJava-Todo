package com.example.finalproject;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private EditText txtFullName, txtEmail, txtPassword, txtCFPassword;
    private Button btnSignUp, btnGetBack;
    private ProgressBar prgProgress;
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

        prgProgress = (ProgressBar) findViewById(R.id.prg_progress);

        btnGetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBacktoSignIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }

    private void getBacktoSignIn() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void signUpUser() {
        String fullName = txtFullName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtCFPassword.getText().toString().trim();

        if (fullName.isEmpty()){
            txtFullName.setError("Fullname is required");
            txtFullName.requestFocus();
            return;
        }

        if (password.isEmpty()){
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            txtPassword.setError("Password need at least 6 characters");
            txtPassword.requestFocus();
            return;
        }

        if (email.isEmpty()){
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please provide valid email");
            txtEmail.requestFocus();
            return;
        }

        if (!confirmPassword.equals(password)){
            txtCFPassword.setError("Password does not match");
            txtCFPassword.requestFocus();
            return;
        }


        prgProgress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            prgProgress.setVisibility(View.GONE);
                            User user = new User(fullName, email);
                            prgProgress.setVisibility(View.GONE);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup.this, "User has been sign up successfully", Toast.LENGTH_LONG).show();
                                        getBacktoSignIn();
                                    }else{
                                        Log.e("signup", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Signup.this, "Failed to sign up, please try again later!!!", Toast.LENGTH_LONG).show();
                                    }
                                    prgProgress.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Signup","login fail");
            }
        });;
    }

}