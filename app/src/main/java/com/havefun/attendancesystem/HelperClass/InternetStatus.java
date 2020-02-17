package com.havefun.attendancesystem.HelperClass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.havefun.attendancesystem.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class InternetStatus  {
   Context mContext;
    public InternetStatus(Context context) {

        mContext=context;
    }

    public Boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        mContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo active_network = cm.getActiveNetworkInfo();

        if (null != active_network) {

            if (active_network.getType() == ConnectivityManager.TYPE_WIFI) {
                StyleableToast.makeText( mContext, "uploading data", R.style.wifi).show();

            }
           else if (active_network.getType() == ConnectivityManager.TYPE_MOBILE) {
                StyleableToast.makeText( mContext, "uploading data", R.style.mobile ).show();

            }
           return true;
        }
            else {


                StyleableToast.makeText( mContext, "No Internet Connection", R.style.noconnection ).show();
            return false;
            }


    }
}

