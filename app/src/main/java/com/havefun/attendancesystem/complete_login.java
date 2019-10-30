package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class complete_login extends AppCompatActivity {
Button b;
EditText user_name,email,phone,address,date_of_birth;
    Animation animate1,animate2;
    ImageView profile,user_icon,email_icon,phone_icon,address_icon,dateofbirth_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_login);
user_name = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);
        date_of_birth = (EditText)findViewById(R.id.dateofbirth);
        user_icon = (ImageView) findViewById(R.id.username_icon);
        email_icon = (ImageView) findViewById(R.id.email_icon);
        phone_icon = (ImageView) findViewById(R.id.phone_icon);
        address_icon = (ImageView) findViewById(R.id.address_icon);
        dateofbirth_icon = (ImageView) findViewById(R.id.date_icon);
        profile= (ImageView) findViewById(R.id.image_profile);



        b = (Button)findViewById(R.id.submit);
b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Animation animate = AnimationUtils.loadAnimation(complete_login.this, R.anim.fadein);
        b.startAnimation(animate);
    }
});
user_name.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
         animate1 = AnimationUtils.loadAnimation(complete_login.this, R.anim.righttoleft);
        user_name.startAnimation(animate1);
    }
});
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 animate1 = AnimationUtils.loadAnimation(complete_login.this, R.anim.righttoleft);
                email.startAnimation(animate1);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 animate1 = AnimationUtils.loadAnimation(complete_login.this, R.anim.righttoleft);
                phone.startAnimation(animate1);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 animate1 = AnimationUtils.loadAnimation(complete_login.this, R.anim.righttoleft);
                address.startAnimation(animate1);
            }
        });
        date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 animate1 = AnimationUtils.loadAnimation(complete_login.this, R.anim.righttoleft);
                date_of_birth.startAnimation(animate1);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.lefttoright);
                profile.startAnimation(animate2);
            }
        });
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.bounce);
                user_icon.startAnimation(animate2);
            }
        });
        email_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.bounce);
                email_icon.startAnimation(animate2);
            }
        });
        phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.bounce);
                phone_icon.startAnimation(animate2);
            }
        });
        address_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.bounce);
                address_icon.startAnimation(animate2);
            }
        });
        dateofbirth_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate2 = AnimationUtils.loadAnimation(complete_login.this, R.anim.bounce);
                dateofbirth_icon.startAnimation(animate2);
            }
        });



    }
}
