package com.havefun.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.havefun.attendancesystem.Chat.MainChat;
import com.havefun.attendancesystem.Profile.ProfileActivity;
import com.havefun.attendancesystem.QR.QrGen;
import com.havefun.attendancesystem.QR.QrGeneration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;



public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    LinearLayout loginactivity,profile_page,Scaner,waiting,qrgeneration;
    FirebaseUser user;
    DBManager offlineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initializeVars();
        addListners();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.menulogout: {
                logOut();
                break;
            }
        }

        return true;
    }
    /*
    **** User info Handling
    */

private void logOut(){
    offlineDB.deleteAllRecordProf();
    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    FirebaseAuth.getInstance().signOut();
}
    /*
     ** Initialization Stage
     */
    private void initializeVars() {
        loginactivity=(LinearLayout)findViewById(R.id.loginactivity);
        profile_page=(LinearLayout)findViewById(R.id.profile_page);
        Scaner=(LinearLayout)findViewById(R.id.Scaner);
        waiting=(LinearLayout)findViewById(R.id.waiting);
        qrgeneration=(LinearLayout)findViewById(R.id.qrgeneration);
        toolbar =(Toolbar) findViewById(R.id.toolbar);

        user= FirebaseAuth.getInstance().getCurrentUser();
        offlineDB=new DBManager(getApplicationContext());
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.draw_dev);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void addListners() {
        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });

        profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });
        Scaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QrGen.class));

            }
        });
        waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainChat.class));

            }
        });
        qrgeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QrGeneration.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
