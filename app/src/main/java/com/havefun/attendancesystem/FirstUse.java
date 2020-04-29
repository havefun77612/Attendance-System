package com.havefun.attendancesystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class FirstUse {
     String TAG="FirstUse.Java";
    SharedPreferences sharedPreferences;
    Context mContext;

    public FirstUse(Context context) {
        mContext=context;
        sharedPreferences= mContext.getSharedPreferences("FirstUse",0);


    }

    public boolean checkTheFirstUse( ) {
    boolean firstUse=sharedPreferences.getBoolean("first",true);
    if (firstUse){
    //       Toast.makeText(mContext, "First USe", Toast.LENGTH_SHORT).show();

   //     sharedPreferences.edit().putBoolean("first",false).apply();
        Log.i(TAG, "checkTheFirstUse: "+"first" );
        return true;
    }else {
        Toast.makeText(mContext, "Not First Use", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "checkTheFirstUse: "+ "Not First Use");
        return false;
    }
    }

    public boolean getUserRateUs(){
        boolean rateUs=sharedPreferences.getBoolean("userRateUs",false);
        if (rateUs){

            return true;
        }else {

            return false;
        }

    }
    public void setUserRateUsToTrue(){

        sharedPreferences.edit().putBoolean("userRateUs",true).apply();
    }

}
