package com.example.finalproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ProjectViewModel {
    private MutableLiveData<List<User>> projects;
    public LiveData<List<User>> getUsers() {
        if (projects == null) {
            projects = new MutableLiveData<List<User>>();
            loadUsers();
        }
        return projects;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.


    }
}
