package com.havefun.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstOpen extends AppCompatActivity {
    int[] layouts;
    int curntscreen = 1;
    Button next, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);

        initializeVars();
    }

    private void initializeVars() {
        layouts = new int[]{R.layout.welcome_slide2, R.layout.welcome_slide3, R.layout.welcome_slide4};
        next = (Button) findViewById(R.id.nextwelcomepage);
        skip = (Button) findViewById(R.id.skip);
        if (curntscreen==4){
            next.setText("SKIP");
            skip.setVisibility(View.INVISIBLE);
            skip.setEnabled(false);
        }
        addclickListner();
    }

    private void addclickListner() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curntscreen == 1) {
                    setContentView(layouts[0]);
                    curntscreen++;
                }else if (curntscreen==2){
                    setContentView(layouts[1]);
                    curntscreen++;
                }else if (curntscreen==3){
                    setContentView(layouts[2]);
                    curntscreen++;
                    next.setText("SKIP");
                }else if (curntscreen==4){
                    startActivity(new Intent(getApplicationContext(),MainPage.class));
                    finish();
                }
                initializeVars();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curntscreen==4){
                    skip.setVisibility(View.INVISIBLE);
                    skip.setEnabled(false);
                }
                startActivity(new Intent(getApplicationContext(),MainPage.class));
                finish();
            }
        });
    }


}