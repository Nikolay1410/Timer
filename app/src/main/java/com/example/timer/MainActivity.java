package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTime;
    private Handler handler;
    private int seconds = 0;
    private int secs = 0;
    private int fet = 0;
    private int minutes;
    private boolean isRunning = false;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTime = findViewById(R.id.textViewTime);
        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonPause = findViewById(R.id.buttonPause);
        Button buttonReset = findViewById(R.id.buttonReset);
        handler = new Handler();
        pb = findViewById(R.id.progressBarToday);

        if(savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }

        runTimer();
        progressBarAnimation();


        buttonStart.setOnClickListener(view -> isRunning = true);

        buttonPause.setOnClickListener(view -> isRunning = false);

        buttonReset.setOnClickListener(view -> {
            isRunning = false;
            seconds = 0;
            fet = 0;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
    }

    //Timer method
    private void runTimer(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                minutes = (seconds%3600)/60;
                secs = seconds%60;
                if (secs == 0){
                    fet = 0;
                }

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                textViewTime.setText(time);

                if (isRunning){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
    private void progressBarAnimation() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    fet++;
                }
                handler.postDelayed(this, 1250);
                pb.setProgress(fet);

            }
        });
    }
}