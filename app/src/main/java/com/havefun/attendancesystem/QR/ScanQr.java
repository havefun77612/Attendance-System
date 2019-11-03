package com.havefun.attendancesystem.QR;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.havefun.attendancesystem.MainPage;
import com.havefun.attendancesystem.R;

public class ScanQr extends AppCompatActivity {
    TextView QrText;
    Button scan_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanqr);
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
        QrText = (TextView) findViewById(R.id.QrText);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 11);
        } else {
            scan_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(ScanQr.this);
                    integrator.setBeepEnabled(false);
                    integrator.setPrompt("Attendance System Scan");
                    integrator.setOrientationLocked(false);
                    integrator.setCameraId(0);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
        super.onBackPressed();
    }
}
