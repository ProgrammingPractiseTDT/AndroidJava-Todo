package com.example.finalproject.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AddResetPasswordDialog extends Dialog implements View.OnClickListener {
    public Dialog d;
    public Activity c;
    public Button yes, no;
    public EditText email;
    private FirebaseAuth mAuth;

    public AddResetPasswordDialog(Activity a){
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recover_password_dialog);
        mAuth = FirebaseAuth.getInstance();
        yes = (Button) findViewById(R.id.btn_submit_reset);
        no = (Button) findViewById(R.id.btn_cancel_reset);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_submit_reset:
                email = (EditText) findViewById(R.id.txt_resetEmail);
                beginRecovery(email.getText().toString());
                dismiss();
                break;
            case R.id.btn_cancel_reset:
                dismiss();
                break;
        }
    }
    ProgressDialog loadingBar;
    private void beginRecovery(String email){
        loadingBar = new ProgressDialog(getContext());
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Log.w("Error",email);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Done sent", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Error Occur", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(getContext(),"Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
