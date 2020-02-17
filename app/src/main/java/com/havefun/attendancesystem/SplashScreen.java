package com.havefun.attendancesystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.havefun.attendancesystem.Authentication.Login;

public class SplashScreen extends AppCompatActivity {
    ImageView image;
    boolean userFirstOpen;
    SharedPreferences firstOpenSharedPreferences;
    FirebaseUser user;
    String TAG = "com.havefun.attendancesystem";
    public static String FirebaseUserType = "Student";

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
                    sleep(1000);
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
        user = FirebaseAuth.getInstance().getCurrentUser();
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

        whichActivityToOpen();
    }

    private void whichActivityToOpen() {
        // if the user firstlly use the application
        if (userFirstOpen) {

            Log.i(TAG, "chechIfUserFirstUse: First Open");
            firstOpenSharedPreferences.edit().putBoolean("isFirstOpen", false).apply();

            // Start the welcome pages of the application
            startActivity(new Intent(getApplicationContext(), FirstOpen.class));
            finish();
        } else {
            // test if the user login to the system or not
            if (user != null) {
                Log.i(TAG, "chechIfUserFirstUse: Not First Open And User Exist");           // start the main page
                getUserType();
            } else {
                Log.i(TAG, "chechIfUserFirstUse: Not First Open And User Not Exist ");
                openLoginPage();
            }

        }
    }

    public void getUserType() {

        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("UserId").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if (dataSnapshot.exists()){
               for (DataSnapshot data : dataSnapshot.getChildren()) {
                   try {
                       FirebaseUserType = data.child("UserType").getValue().toString();
                       Toast.makeText(SplashScreen.this, data.child("UserType").getValue().toString(), Toast.LENGTH_SHORT).show();
                   }catch (Exception e){
                       Toast.makeText(SplashScreen.this, "Old USer Structure", Toast.LENGTH_SHORT).show();
                   }
                   Log.i(TAG, "onDataChange: user type is "+ FirebaseUserType);
               }
               // FancyToast.makeText(getApplicationContext(), "User type is " + FirebaseUserType,FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
               openMainPage();
           }else {
               Toast.makeText(SplashScreen.this, "No Data for this user", Toast.LENGTH_SHORT).show();
               openMainPage();
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openLoginPage() {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    private void openMainPage() {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
    }
}
