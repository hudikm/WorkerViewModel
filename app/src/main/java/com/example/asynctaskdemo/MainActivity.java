package com.example.asynctaskdemo;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WorkerFragment workerFragment = (WorkerFragment) getSupportFragmentManager().findFragmentByTag("WorkerFragment");
        if (workerFragment == null) {
            workerFragment = new WorkerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(workerFragment, "WorkerFragment")
                    .commit();

            workerFragment.runAsyncTask();
        }

    }
}
