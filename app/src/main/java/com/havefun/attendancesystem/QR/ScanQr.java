package com.havefun.attendancesystem.QR;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
//import com.havefun.attendancesystem.MainPage;
import com.havefun.attendancesystem.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ScanQr extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    TextView QrText;
    Button scan_btn,send;
    ZXingScannerView zx;
    static ArrayList<String> array=new ArrayList<String>();
    //static ArrayList<String[]> array2=new ArrayList<String[]>();
    MediaPlayer mp,mp2;
    Intent res;
    String[]qrRes;
    final HashMap<String, HashMap<String,String>> hashMap=new HashMap<String, HashMap<String, String>>();
    //int[]index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_start);
        initialVariabels();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You Didn't Scan Any Barcode", Toast.LENGTH_SHORT).show();
                QrText.setText("You Didn't Scan Any Barcode");
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                QrText.setText(result.getContents());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    /*
    :::::::: initialization Stage ::::::::::::
     */
    public void initialVariabels() {
        scan_btn = (Button) findViewById(R.id.scan_btn);
        //QrText = (TextView) findViewById(R.id.QrText);
        send = (Button) findViewById(R.id.send);
        mp=MediaPlayer.create(ScanQr.this,R.raw.beep);
        mp2=MediaPlayer.create(ScanQr.this,R.raw.error);



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
        } else {scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scane();


            }
        });
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!array.isEmpty()){
                        for(int i=0;i<array.size();i++){
                            //split(array.get(i));
                            qrRes=array.get(i).split("/");
                            final HashMap<String, String> hash = new HashMap<String, String>();
                            hash.put("UserName",qrRes[0]);
                            hash.put("UserID",qrRes[1]);
                            hash.put("UserEmail",qrRes[2]);
                            hash.put("UserPhone",qrRes[3]);
                            hash.put("UserAddress",qrRes[4]);
                            hash.put("UserDate",qrRes[5]);
                            hashMap.put("hash"+i,hash);
                            //array2.add(qrRes);
                            //res=new Intent(ScanQr.this,ressult.class);
                            //res.putExtra("results",qrRes);
                            //startActivity(res);


                        }
                        res=new Intent(ScanQr.this,ressult.class);
                        res.putExtra("qrResults",array);
                        try {
                            startActivity(res);
                        }catch (Exception e){
                            startActivity(new Intent(getApplicationContext(),ScanQr.class));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"you didn't scan any thing",Toast.LENGTH_LONG).show();
                    }//Toast.makeText(getApplicationContext(),hashMap.get("hash1").get("UserName"),Toast.LENGTH_LONG).show();

                }
            });

        }
    }
    public void scane(){
        zx=new ZXingScannerView(getApplicationContext());
        setContentView(zx);
        zx.setResultHandler(this);
        zx.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(zx!=null){zx.stopCamera();}



    }

    @Override
    public void onBackPressed() {
        if(zx!=null){
            zx.removeAllViews();//here comes the crash
            startActivity(new Intent(getApplicationContext(),ScanQr.class));
            finish();




            super.onBackPressed();
        }else{
            super.onBackPressed();
        }



    }

    @Override
    public void handleResult(Result result) {
        if(!array.contains(result.getText())&&result.getText().contains("@x@")){
            array.add(result.getText());
            mp.start();
            Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
            zx.resumeCameraPreview(this);
        }else {
            mp2.start();
            zx.resumeCameraPreview(this);
        }
    }

}
