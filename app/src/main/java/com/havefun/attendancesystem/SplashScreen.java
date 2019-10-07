package com.havefun.attendancesystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    ImageView image;
    boolean userFirstOpen;
    SharedPreferences firstOpenSharedPreferences;
    String TAG = "com.havefun.attendancesystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        initializeVars();
        Thread SplashThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(3000);
                } catch (Exception e) {

                } finally {

                    // Check if this is the first laungh of the app or opened before
                    chechIfUserFirstUse();
                }
            }
        };
        SplashThread.start();
    }

    /*
     ** Initialization Stage Of the Application
     */
    private void initializeVars() {
        image = (ImageView) findViewById(R.id.splashImage);
        firstOpenSharedPreferences = getApplicationContext().getSharedPreferences("FirstOpen", MODE_PRIVATE);
        userFirstOpen = firstOpenSharedPreferences.getBoolean("isFirstOpen", true);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    /*
    :::: An important Method Used To Know If This is the first launch of the application
     */
    private void chechIfUserFirstUse() {
        if (userFirstOpen) {

            Log.i(TAG, "chechIfUserFirstUse: First Open");
            firstOpenSharedPreferences.edit().putBoolean("isFirstOpen", false).apply();

            // Start the welcome pages of the application
            startActivity(new Intent(getApplicationContext(), FirstOpen.class));
            finish();
        } else {
            Log.i(TAG, "chechIfUserFirstUse: Not First Open");           // start the main page
            startActivity(new Intent(getApplicationContext(), MainPage.class));
            finish();
        }
    }
}
