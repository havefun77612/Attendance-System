package com.havefun.attendancesystem.Profile;

import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.havefun.attendancesystem.R;


public class Profiletest extends AppCompatActivity {

Button backtohome;
Animation Animate1,Animate2;
ImageView profile_image,name_image,email_image,phone_image,address_image,date_image;
TextView accountinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        IntialVar();
        addanimation();
    }

  public void   IntialVar (){
    backtohome =(Button)findViewById( R.id.back_to_home );
    profile_image = (ImageView) findViewById( R.id.userImage );
    accountinfo = (TextView)findViewById( R.id.accountinfo );
    name_image = (ImageView)findViewById( R.id.name_image );
    address_image = (ImageView)findViewById( R.id.address_image );
    phone_image = (ImageView)findViewById( R.id.mobile_image );
    date_image = (ImageView)findViewById( R.id.date_image );
    email_image = (ImageView)findViewById( R.id.email_image );
    }

    public void addanimation(){
    Animate1 = AnimationUtils.loadAnimation( com.havefun.attendancesystem.Profile.Profiletest.this,R.anim.lefttoright );
    Animate2 = AnimationUtils.loadAnimation( com.havefun.attendancesystem.Profile.Profiletest.this , R.anim.righttoleft );
    backtohome.startAnimation( Animate1 );
    profile_image.startAnimation( Animate1 );
    accountinfo.startAnimation( Animate1 );
    name_image.startAnimation( Animate2 );
    address_image.startAnimation( Animate2 );
    phone_image.startAnimation( Animate2 );
    date_image.startAnimation( Animate2 );
    email_image.startAnimation( Animate2 );
    }
}
