package com.havefun.attendancesystem.QR;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.havefun.attendancesystem.QR.ScanCourse.selectedLevel;

//import com.havefun.attendancesystem.MainPage;


public class ScanQr extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    TextView QrText;
    Button scan_btn, send;
    ZXingScannerView zx;
    ScanCourse scanCourse=new ScanCourse();

    public static ArrayList<String> array = new ArrayList<String>();
    //static ArrayList<String[]> array2=new ArrayList<String[]>();
    MediaPlayer mp, mp2;
    Intent res;
    String[] qrRes;
    public static ArrayList<UserData> scanData = new ArrayList<UserData>();
    //final HashMap<String,UserData>sendData=new HashMap<String, UserData>();
    //final HashMap<String, HashMap<String,String>> hashMap=new HashMap<String, HashMap<String, String>>();
    //int[]index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_start);
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
        send = (Button) findViewById(R.id.send);
        mp = MediaPlayer.create(ScanQr.this, R.raw.beep);
        mp2 = MediaPlayer.create(ScanQr.this, R.raw.error);

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
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!array.isEmpty()) {
                        res = new Intent(ScanQr.this, ressult.class);
                        try {
                            startActivity(res);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), ScanQr.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "you didn't scan any thing", Toast.LENGTH_LONG).show();
                    }
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


        if (!array.contains(result.getText()) && result.getText().contains("@x@")) {
            array.add(result.getText());
            qrRes = result.getText().split("/");
            UserData qrData = new UserData();
            qrData.setName(qrRes[0]);
            qrData.setID(qrRes[1]);
            qrData.setEmail(qrRes[2]);
            qrData.setPhone(qrRes[3]);
            qrData.setAddress(qrRes[4]);
            qrData.setDate(qrRes[5]);
            qrData.setLevel(qrRes[6]);
            if (qrData.getLevel().equals(selectedLevel)) {
                scanData.add(qrData);
            } else {
                Log.i("Scanning Qr", "handleResult: Wrong " + selectedLevel);
                Toast.makeText(this, "handleResult: Wrong " + selectedLevel, Toast.LENGTH_SHORT).show();
            }

            mp.start();
            Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_LONG).show();
            zx.resumeCameraPreview(this);
        }else if(!scanCourse.course){
            startActivity(new Intent(ScanQr.this, ScanCourse.class));

        }
        else {
            mp2.start();
            zx.resumeCameraPreview(this);
        }

    }

    public void mod(String temp, int pos) {
        scanData.get(pos).setName(temp);


    }

    public void del(int pos) {
        scanData.remove(pos);
    }


}
