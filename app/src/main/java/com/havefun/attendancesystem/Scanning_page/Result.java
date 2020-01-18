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
public class Result extends Fragment {

Button btn;
Animation animate1;
    public Result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate( R.layout.scanning_result, container, false);
    }

}
