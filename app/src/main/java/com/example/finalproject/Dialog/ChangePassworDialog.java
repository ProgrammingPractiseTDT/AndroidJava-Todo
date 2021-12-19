package com.example.finalproject.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.HomeScreen;
import com.example.finalproject.Login;
import com.example.finalproject.R;
import com.example.finalproject.StartingScreen;
import com.example.finalproject.UserProfile;

import io.paperdb.Paper;

public class ChangePassworDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes,no;
    public EditText txt_oldPassword, txt_newPassword;

    public ChangePassworDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_password_dialog);
        yes = (Button) findViewById(R.id.btn_confirmChangePassword);
        no = (Button) findViewById(R.id.btn_cancelChangePassword);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        txt_oldPassword = (EditText) findViewById(R.id.txt_oldPassword);
        txt_newPassword = (EditText) findViewById(R.id.txt_newPassword);
        switch (view.getId()){
            case R.id.btn_confirmChangePassword:
                FirebaseOperator Fo = new FirebaseOperator();
                Fo.changePasssword(txt_oldPassword.getText().toString(), txt_newPassword.getText().toString());
                Paper.book().destroy();
                Toast.makeText(getContext(),c.getString(R.string.changePasswordSuccess),Toast.LENGTH_LONG).show();
                dismiss();
                break;
            case R.id.btn_cancelChangePassword:
                dismiss();
                break;
        }

    }
}
