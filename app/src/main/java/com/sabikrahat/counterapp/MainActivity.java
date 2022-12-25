package com.sabikrahat.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private Button startBtn, resetBtn, pauseBtn;
    int counter = 2019160256;

    boolean isCounting = false;
    boolean isPause = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        displayText = findViewById(R.id.display);
        startBtn = findViewById(R.id.startBtn);
        resetBtn = findViewById(R.id.resetBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        //
        findViewById(R.id.exitBtn).setOnClickListener(view -> finish());
        startBtn.setOnClickListener(view -> startCounting());
        resetBtn.setOnClickListener(view -> resetCounting());
        pauseBtn.setOnClickListener(view -> pauseCounting());
    }

    private void startCounting() {
        if (!isCounting) {
            isCounting = true;
            CountingAsyncTask task = new CountingAsyncTask();
            task.execute(isCounting);
        } else if (isPause) {
            isPause = false;
            pauseBtn.setText("Pause");
        } else {
            Toast.makeText(this, "Already running.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetCounting() {
        if (counter != 2019160256) {
            isPause = true;
            counter = 2019160256;
            displayText.setText(String.valueOf(counter));
            pauseBtn.setText("Resume");
        } else {
            Toast.makeText(this, "Already initial value set.", Toast.LENGTH_SHORT).show();
        }

    }

    private void pauseCounting() {
        //...
        if(isCounting){
            if (isPause) {
                isPause = false;
                pauseBtn.setText("Pause");
            } else {
                isPause = true;
                pauseBtn.setText("Resume");
            }
        } else {
            Toast.makeText(this, "You haven't start the counter.", Toast.LENGTH_SHORT).show();
        }
        
    }

    private class CountingAsyncTask extends AsyncTask<Boolean, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayText.setText(String.valueOf(counter));
        }

        @Override
        protected Integer doInBackground(Boolean... booleans) {
            while (booleans[0]) {
                if (!isPause) {
                    counter--;
                    publishProgress(counter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return counter;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            displayText.setText(String.valueOf(values[0]));
        }
    }
}