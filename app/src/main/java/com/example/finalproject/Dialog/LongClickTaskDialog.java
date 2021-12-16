package com.example.finalproject.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.finalproject.FirebaseOperator;
import com.example.finalproject.R;


public class LongClickTaskDialog extends Dialog implements
        android.view.View.OnClickListener {

    public android.content.Context c;
    public Dialog d;
    public Button delete, edit;
    private String projectKey;
    private String tasksKey;
    public LongClickTaskDialog(Context a, String projectKey, String tasksKey) {
        super(a);
        this.c = a;
        this.projectKey=projectKey;
        this.tasksKey = tasksKey;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.long_touch_option_dialog);


        delete = (Button) findViewById(R.id.delete_project_btn);
        edit = (Button) findViewById(R.id.edit_project_btn);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_project_btn:
                FirebaseOperator firebaseOperator = new FirebaseOperator();
                firebaseOperator.deleteTasks(projectKey, tasksKey);
                dismiss();
                break;
            case R.id.edit_project_btn:
                EditProjectDialog editProjectDialog = new EditProjectDialog(v.getContext(), projectKey);
                editProjectDialog.show();
                dismiss();
                break;
            default:
                break;
        }
    }

}
