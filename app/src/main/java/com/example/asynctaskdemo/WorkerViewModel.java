package com.example.asynctaskdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class WorkerViewModel extends ViewModel {
    private List<Integer> data = new ArrayList<>();
    private AsyncTask<Void, Integer, Void> asyncTask;
    private MutableLiveData<List<Integer>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Integer>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void runAsyncTask() {
        if (asyncTask == null)
            asyncTask = new AsyncTaskWorker().execute();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        asyncTask.cancel(true);
    }

    public class AsyncTaskWorker extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int i = 0;
            while (true) {
                try {
                    publishProgress(i++);
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if (isCancelled()) return null;
                }

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            data.add(values[0]);
            mutableLiveData.setValue(data);
        }
    }
}


