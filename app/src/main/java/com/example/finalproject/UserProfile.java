package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DataClass.User;
import com.example.finalproject.Dialog.ChangePassworDialog;
import com.example.finalproject.Dialog.ChangeUsernameDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    Button changeUsername, changePassword;
    TextView txt_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        txt_username =  (TextView) findViewById(R.id.txt_Username);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
        if(user!=null){
            String uid = user.getUid();

            reference.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    txt_username.setText(user.getFullName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




        changePassword = (Button) findViewById(R.id.btn_changePassword);
        changeUsername = (Button) findViewById(R.id.btn_changeUserName);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUsernameDialog dia = new ChangeUsernameDialog(UserProfile.this);
                dia.show();
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassworDialog dia = new ChangePassworDialog(UserProfile.this);
                dia.show();
            }
        });
    }

}
