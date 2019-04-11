package com.example.asynctaskdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkerFragment extends Fragment {
    private List<Integer> data = new ArrayList<>();
    private MyRecyclerViewAdapter myRecyclerViewAdapter = null;
    private RecyclerView recyclerView = null;
    private AsyncTask<Void, Integer, Void> asyncTask;

    public void runAsyncTask() {

        asyncTask = new AsyncTaskWorker().execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.list);
        if (myRecyclerViewAdapter == null) {
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(data);
        }
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }


    @Override
    public void onPause() {
        super.onPause();
        recyclerView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }

    public class AsyncTaskWorker extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i++);
                    if (isCancelled()) return null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            myRecyclerViewAdapter.getData().add(values[0]);
            myRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

}
