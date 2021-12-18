package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.Dialog.AddResetPasswordDialog;
import com.google.firebase.auth.FirebaseAuth;

public class EditTaskActivity extends AppCompatActivity {
    public TextView title;
    public DatePicker datePicker;
    public TimePicker timePicker;
    public RadioGroup radioGroup;
    public TextView description;
    public AppCompatButton apply_btn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_layout);

        String projectKey = getIntent().getStringExtra("projectKey");
        String taskKey = getIntent().getStringExtra("taskKey");
        title = findViewById(R.id.txt_taskTitleInput);
//        title.setText("oke");
        datePicker = findViewById(R.id.datePicker);
        description = findViewById(R.id.txt_descriptionEdit);
        timePicker = findViewById(R.id.timePicker);

        radioGroup = findViewById(R.id.rad_priority);




        FirebaseOperator firebaseOperator = new FirebaseOperator();
        firebaseOperator.autofillFromTask(projectKey,taskKey,title, datePicker, description, timePicker, radioGroup);
        apply_btn = findViewById(R.id.add_dialog_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFormat = Integer.toString(datePicker.getDayOfMonth()) + "-" + Integer.toString(datePicker.getMonth() + 1) + "-" + Integer.toString(datePicker.getYear());
                String timeString = Integer.toString(timePicker.getHour())+':'+ Integer.toString(timePicker.getMinute());
                int priority_id = radioGroup.getCheckedRadioButtonId();
                int priority_number = checkPriority(priority_id);
                firebaseOperator.editTask(projectKey, taskKey, title.getText().toString(), dateFormat, timeString, description.getText().toString(), priority_number);
                finish();
            }
        });

    }
    private int checkPriority(int id){
        RadioButton radioButton = findViewById(id);
        String text_priority = radioButton.getText().toString();
        switch (text_priority){
            case "high":
            case "cao":
                return 3;
            case "medium":
            case "vừa":
                return 2;
            case "low":
            case "thấp":
                return 1;
            default:
                return 0;
        }
    }
}
