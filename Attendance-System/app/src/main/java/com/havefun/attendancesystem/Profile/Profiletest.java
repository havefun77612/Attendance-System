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
Animation Animate1;
ImageView image;
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
    image = (ImageView) findViewById( R.id.userImage );
    accountinfo = (TextView)findViewById( R.id.accountinfo );
    }

    public void addanimation(){
    Animate1 = AnimationUtils.loadAnimation( com.havefun.attendancesystem.Profile.Profiletest.this,R.anim.lefttoright );
    backtohome.startAnimation( Animate1 );
    image.startAnimation( Animate1 );
    accountinfo.startAnimation( Animate1 );
    }
}
