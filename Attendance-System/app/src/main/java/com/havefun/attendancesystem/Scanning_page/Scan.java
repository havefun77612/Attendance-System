package com.havefun.attendancesystem.Scanning_page;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.havefun.attendancesystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scan extends Fragment {
Button button;
Animation animate;

    public Scan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {


 return inflater.inflate( R.layout.scanning_start, container, false);
    }




    /*
    @Override
    public void onResume() {
        super.onResume();
        if(anim!=null && !anim.isRunning()){
            anim.start();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(anim!=null && anim.isRunning()){
            anim.stop();
        }
    }
*/
}

  /*
        intVars();
        anim = (AnimationDrawable) button.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);
        */
