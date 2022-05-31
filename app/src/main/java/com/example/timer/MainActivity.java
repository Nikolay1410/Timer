package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTime;
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonReset;
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
        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonReset = findViewById(R.id.buttonReset);
        handler = new Handler();
        pb =  (ProgressBar) findViewById(R.id.progressBarToday);

        if(savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }

        runTimer();
        progressBarAnimation();


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = true;
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
                seconds = 0;
                fet = 0;
            }
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