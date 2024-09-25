package com.example.launchertest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To start app again if it crashes
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // Restart the app
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                // Kill the current process
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        setContentView(R.layout.activity_main);

        setImmersiveMode();
    }

    private void setImmersiveMode() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    public void simulateCrash(View v) {
        throw new RuntimeException("This is a crash");
    }

    public void simulateExit(View v) {
        finishAndRemoveTask();
    }

    public void simulateReboot(View v) {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pm.reboot("To try a feature of this app");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Do nothing, effectively disabling the back button
    }
}