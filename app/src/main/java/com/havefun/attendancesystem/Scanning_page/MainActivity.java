package com.havefun.attendancesystem.Scanning_page;

import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.havefun.attendancesystem.R;

public class MainActivity extends AppCompatActivity {
     Toolbar toolbar;
     TabLayout tabLayout;
     ViewPagerAdapter viewPagerAdapter;
     ViewPager viewPager;
     Button button;
    Animation Animate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.scanning_page );
        intVars();
        setSupportActionBar( toolbar );
        setupViewPager( viewPager );
        tabLayout.setupWithViewPager( viewPager );
  //      addanimation();

    }

    public void intVars() {

        toolbar = (Toolbar) findViewById( R.id.toolbar1 );
        tabLayout = (TabLayout) findViewById( R.id.tablayout1 );
        viewPager = (ViewPager) findViewById( R.id.viewpager1 );
        button = (Button) findViewById( R.id.search_btn );

    }
    /*
private void addanimation(){

        Animate1 = AnimationUtils.loadAnimation( MainActivity.this, R.anim.lefttoright );
        button.startAnimation( Animate1 );
}
*/
    private void setupViewPager(ViewPager viewpager) {

        viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager() );
        viewPagerAdapter.addFragment( new Scan(), "Scan" );
        viewPagerAdapter.addFragment( new Result(), "Result" );
        viewPager.setAdapter(viewPagerAdapter );
        tabLayout.setupWithViewPager( viewPager );
    }

}


    /*
public void addanimation(){
Animate1 = AnimationUtils.loadAnimation( MainActivity.this,R.anim.lefttoright);
button.startAnimation( Animate1 );
    }
/*
    @Override
protected void onResume() {
    super.onResume();
    if(anim!=null && !anim.isRunning()){
        anim.start();
    }
}
    @Override
   protected void onPause() {
       super.onPause();
     if(anim!=null && anim.isRunning()){
         anim.stop();
       }
   }



  button.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            Animate1 = AnimationUtils.loadAnimation( MainActivity.this,R.anim.lefttoright);
            button.startAnimation( Animate1 );
        }
    });
*/


