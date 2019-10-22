package com.havefun.attendancesystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {
    TextView QrText;
    Button scan_btn;
    String Content = "";
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialVariabels();

    }

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
                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                    integrator.setBeepEnabled(false);
                    integrator.setPrompt("Scan Now");
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
}
