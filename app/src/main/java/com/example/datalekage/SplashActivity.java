package com.example.datalekage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    Timer timer;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI( currentUser );
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {

            timer = new Timer(  );
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                }
            }, 5000);
        } else {
            timer = new Timer(  );
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }, 5000);
        }
    }

}
