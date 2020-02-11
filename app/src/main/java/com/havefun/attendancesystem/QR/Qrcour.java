package com.havefun.attendancesystem.QR;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.havefun.attendancesystem.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Qrcour extends AppCompatActivity {
    EditText name,code,imageNameText;
    ImageView qr;
    Button gen, saveQr;
    QRCodeWriter writer;
    String qrName, qrCode,type;
    Bitmap ffinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursegen);
        vars();
        listener();


    }
    public void vars() {
        name = findViewById(R.id.cname);
        code = findViewById(R.id.code);

        qr = findViewById(R.id.qr);

        gen = findViewById(R.id.gen);
        saveQr = (Button) findViewById(R.id.save);
        writer = new QRCodeWriter();
        qrName = name.getText().toString();
        qrCode=code.getText().toString();
        type="course";



    }
    public void listener() {

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitMatrix matrix = null;
                qrName=name.getText().toString();
                qrCode=code.getText().toString();

                try {

                    matrix = writer.encode(qrName + "/" + qrCode + "/" + type, BarcodeFormat.QR_CODE, qr.getWidth(), qr.getHeight());

                } catch (WriterException e) {
                    Toast.makeText(Qrcour.this, "Error Creating image", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                BarcodeEncoder encoder = new BarcodeEncoder();
                ffinal = encoder.createBitmap(matrix);


                qr.setImageBitmap(ffinal);
            }
        });

        saveQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareQrToBeSaved();
            }
        });
    }
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    private void prepareQrToBeSaved() {
        if (isStoragePermissionGranted()) {
            openDialoge();

        } else {
            Toast.makeText(this, "We need storage permission to save the image", Toast.LENGTH_SHORT).show();
        }

    }
    private void openDialoge() {

        // prepare the alertdialoge data
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image Name");
        builder.setIcon(R.drawable.mop);
        builder.setMessage("Enter the image name OR leave the default...");
        imageNameText = new EditText(getApplicationContext());
        imageNameText.setText(code.getText().toString());
        builder.setView(imageNameText);
        /// setting the saving function
        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    String QrName = imageNameText.getText().toString();
                    Log.i("Imagename", "onClick: Imagename is "+QrName);
                    saveImagefunction(QrName);
                } catch (Exception e) {
                    Toast.makeText(Qrcour.this, "Sorry, we running into a problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /// setting for cancelling the dialogue
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Qrcour.this, "Image not saved", Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();
    }
    private void saveImagefunction(String qrName) {
        MediaStore.Images.Media.insertImage(getContentResolver(), ffinal, qrName , "Attendance system Qr medal");
        try {
            addMedia(getApplication(),ffinal,qrName);
        } catch (IOException e) {
            Toast.makeText(this, "Sorry, Error saving image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public static void addMedia(Context c, Bitmap bitmap, String filename) throws IOException {
        //create a file to write bitmap data
        File f = new File(c.getCacheDir(), filename);
        f.createNewFile();
//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(f));
        c.sendBroadcast(intent);
        Toast.makeText(c, "Image saved ", Toast.LENGTH_SHORT).show();
    }
}
