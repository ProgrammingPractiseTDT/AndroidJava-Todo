package com.example.finalproject.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.finalproject.DataClass.User;
import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.HomeScreen;
import com.example.finalproject.R;
import com.example.finalproject.UserProfile;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeUsernameDialog extends Dialog implements View.OnClickListener{
    public Activity c;
    public Dialog d;
    public Button yes,no;
    public EditText txt_userName;

    public ChangeUsernameDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_username_dialog);
        yes = (Button) findViewById(R.id.btn_confirmChangedUsername);
        no = (Button) findViewById(R.id.btn_cancelChangedUsername);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
     }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirmChangedUsername:
                txt_userName = (EditText) findViewById(R.id.txt_changedUserName);
                if (txt_userName.getText().toString().isEmpty()){
                    txt_userName.setError("This field cannot be empty");
                    txt_userName.requestFocus();
                }
                else{
                    FirebaseOperator changeUserName = new FirebaseOperator();
                    changeUserName.updateUserName(txt_userName.getText().toString());
                    Toast.makeText(getContext(), c.getString(R.string.changeUsernameSuccess), Toast.LENGTH_LONG).show();
                    dismiss();
                }
            case R.id.btn_cancelChangedUsername:
                dismiss();
                break;
        }
    }
}
