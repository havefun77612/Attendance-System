package com.havefun.attendancesystem.QR;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.havefun.attendancesystem.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//import com.havefun.attendancesystem.MainPage;


public class ScanCourse extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    TextView QrText;
    Button scan_btn;
    ZXingScannerView zx;
    static boolean course=false;


    MediaPlayer mp, mp2;
    Intent res;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursescan);
        initialVariabels();
        addListners();

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

        mp = MediaPlayer.create(ScanCourse.this, R.raw.beep);
        mp2 = MediaPlayer.create(ScanCourse.this, R.raw.error);


    }

    public void addListners() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
        } else {
            scan_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scane();
                }
            });


        }

    }

    public void scane() {
        zx = new ZXingScannerView(getApplicationContext());
        setContentView(zx);
        zx.setResultHandler(this);
        zx.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (zx != null) {
            zx.stopCamera();
        }


    }

    @Override
    public void onBackPressed() {
        if (zx != null) {
            zx.removeAllViews();//here comes the crash
            startActivity(new Intent(getApplicationContext(), ScanQr.class));
            finish();


            super.onBackPressed();
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public void handleResult(Result result) {
        if(result.getText().contains("course")){
            course=true;
            mp.start();
            res = new Intent(ScanCourse.this, ScanQr.class);
            try {
                startActivity(res);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ScanCourse.class));
            }


        }



         else {
            mp2.start();
            zx.resumeCameraPreview(this);
        }

    }




}
