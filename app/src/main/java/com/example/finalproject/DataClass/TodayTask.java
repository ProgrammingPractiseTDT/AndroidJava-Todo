package com.example.finalproject.DataClass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class TodayTask extends AppCompatActivity {

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.today_task_layout);
    }
}
