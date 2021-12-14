package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private TextView txt_signUp;
    private FirebaseAuth mAuth;
    private CheckBox cb_rememberMe;
    ArrayList<String> ProjectNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txt_signUp = (TextView) findViewById(R.id.txt_signUp);

        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });


        //sign in
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void goToSignUp(){
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        EditText emailTextField = findViewById(R.id.login_email);
        EditText passwordTextField = findViewById(R.id.login_password);

        String email = emailTextField.getText().toString().trim();
        String password = passwordTextField.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signin", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("error", "signInWithEmail:failure");
                            Toast.makeText(Login.this, "Authentication failed.\n" + email + "\n"+ password + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    public void updateUI(FirebaseUser account){
        ProjectNames= new ArrayList<String>();
        ProjectNames.add("Order");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("projects",ProjectNames);
        Intent intent = new Intent(this, HomeScreen.class);
        if(account != null){
            Toast.makeText(this,ProjectNames.get(0),Toast.LENGTH_LONG).show();
            startActivity(intent);
        }else {
            Toast.makeText(this,"Invalid email or password.",Toast.LENGTH_LONG).show();
        }

    }

    ArrayList<String> getProjectFromUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<String> projectNamesArray = new ArrayList<String>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("projects");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Project project = dsp.getValue(Project.class);
                    projectNamesArray.add(project.getProjectName()); //add result into array list
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
        return projectNamesArray;
    }
}