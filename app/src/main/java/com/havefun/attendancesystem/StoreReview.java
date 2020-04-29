package com.havefun.attendancesystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StoreReview {
   private Context mContext;
   private AppCompatActivity appCompatActivity;

    public StoreReview(Context mContext, AppCompatActivity appCompatActivity) {
        this.mContext = mContext;
        this.appCompatActivity = appCompatActivity;
    }

    public void showReviewDialog(){
        final FirstUse userRateUs =new FirstUse(mContext);
        Drawable color=appCompatActivity.getResources().getDrawable(R.mipmap.ic_launcher);
        if (!userRateUs.getUserRateUs()){
        new AlertDialog.Builder(appCompatActivity,R.style.AppCompatAlertDialogStyle)
        .setTitle("Rate Us 😍 😍")
        .setMessage("If you like our app please rate us , thanks")
        .setPositiveButton("Yse,i'll", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String appPackageName = appCompatActivity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    appCompatActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    appCompatActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                userRateUs.setUserRateUsToTrue();
            }
        })
        .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                appCompatActivity.finish();
            }
        })
         .create().show();
        }else {
            String[] advices={"You can chat with us using chatting page if you have any question or chat with other students  ",
                    "Remember that you always can see your supervisors through our app ",
                    " Qr is a great technology that we use in our app to take your attendance ,you will get emails from your Dr if you have any problem with your attendance"};
            Random rand = new Random();
            // Generate random integers in range 0 to 999
            int position = rand.nextInt(3);
            new AlertDialog.Builder(appCompatActivity,R.style.AppCompatAlertDialogStyle)
                    .setTitle("Today Advice")
                    .setMessage(advices[position])
                    .setPositiveButton("Thank you", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            appCompatActivity.finish();
                        }
                    }).create().show();
        }

    }

    private void sendMeEmail(String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"havefun77612@outlook.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Sam3ny Problem");
        i.putExtra(Intent.EXTRA_TEXT   , content);
        try {
            appCompatActivity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Log.i("StoreReview", "sendMeEmail: "+"There are no email clients installed.");
        }
    }
}
