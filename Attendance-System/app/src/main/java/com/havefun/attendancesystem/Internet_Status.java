package com.havefun.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.havefun.attendancesystem.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class Internet_Status extends AppCompatActivity implements View.OnClickListener {

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.internet_status );
        b = (Button) findViewById( R.id.button );
//b.setOnClickListener( (View.OnClickListener) this );
        b.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService( Context.CONNECTIVITY_SERVICE );
        getApplicationContext().getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo active_network = cm.getActiveNetworkInfo();

        if (null != active_network) {

            if (active_network.getType() == ConnectivityManager.TYPE_WIFI) {
                StyleableToast.makeText( Internet_Status.this, "uploading data",R.style.wifi).show();

            }
           else if (active_network.getType() == ConnectivityManager.TYPE_MOBILE) {
                StyleableToast.makeText( Internet_Status.this, "uploading data", R.style.mobile ).show();

            }
        }
            else {


                StyleableToast.makeText( Internet_Status.this, "No Internet Connection", R.style.noconnection ).show();

            }


    }
}

