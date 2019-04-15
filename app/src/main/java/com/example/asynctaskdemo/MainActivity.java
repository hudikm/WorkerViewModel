package com.example.asynctaskdemo;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.list);

        final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);

        // Najdenie instancie workViewModel triedy
        WorkerViewModel workerViewModel = ViewModelProviders.of(this).get(WorkerViewModel.class);

        LiveData<List<Integer>> liveData = workerViewModel.getMutableLiveData();

        myRecyclerViewAdapter.setData(liveData.getValue());

        // Vytvorenie observera, ktory reaguje na zmenu dat.
        liveData.observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                myRecyclerViewAdapter.setData(integers);
            }
        });
        workerViewModel.runAsyncTask();


    }
}
