package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


//test
public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<String> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listview main menu
        menu = getMenuItem();
        lv = findViewById(R.id.listview_mainMenu);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(this, menu);
        lv.setAdapter(mainMenuAdapter);

    }

    private List<String> getMenuItem(){
        List<String> menu = new ArrayList<String>();
        menu.add("Today");
        menu.add("Important");
        menu.add("All tasks");

        return menu;
    }

    public void showAddProjectDialog(View view) {
        AddProjectDialog cdd=new AddProjectDialog(MainActivity.this);
        cdd.show();
    }
}