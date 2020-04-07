package com.havefun.attendancesystem.QR;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.havefun.attendancesystem.MainPage;
import com.havefun.attendancesystem.R;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class QrGeneration extends AppCompatActivity {
    EditText name,id,email,pnumber,address,date;
    ImageView qr;
    Button dateset,genbtn;
    String userDate;
    QRCodeWriter writer;
    String qrName,qrId,qrEmail,qrPnumber,qrAddress,qrDate;
    Animation Animate1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_generator);
        vars();
        listener();
        adding_animation();

    }
    public void vars(){
        name=(EditText) findViewById(R.id.username);
        id=(EditText) findViewById(R.id.Inputid);
        email=(EditText) findViewById(R.id.email);
        pnumber=(EditText) findViewById(R.id.phone);
        address=(EditText) findViewById(R.id.address);
        date=(EditText) findViewById(R.id.Inputdate);
        qr=(ImageView) findViewById(R.id.qr);
        dateset=(Button) findViewById(R.id.dateset);
        genbtn=(Button) findViewById(R.id.genbtn);
        writer=new QRCodeWriter();


    }
    public void adding_animation(){
        Animate1 = AnimationUtils.loadAnimation( QrGeneration.this , R.anim.rotate );
        qr.startAnimation( Animate1 );


    }
    public void listener(){
        dateset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override

            public void onClick(View v) {
                //Calendar calendar = Calendar.getInstance();//defining a calendar
                int year =1990;//the year
                int month =1; //calendar.get(Calendar.MONTH);//the month
                int day =1; //calendar.get(Calendar.DAY_OF_MONTH);//the day
                //the date picker
                DatePickerDialog dateBD=new DatePickerDialog(QrGeneration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //month is set to 0 so we need to add 1
                        userDate=Integer.toString(day)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year);
                        date.setText(userDate);

                    }
                }, year, month, day);
                dateBD.show();


            }
        });
        genbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrName=name.getText().toString();
                qrId=id.getText().toString();
                qrEmail=email.getText().toString();
                qrPnumber=pnumber.getText().toString();
                qrAddress=address.getText().toString();
                qrDate=date.getText().toString();

                BitMatrix matrix= null;
                try {
                    matrix = writer.encode(qrName+"/"+qrId+"/"+qrEmail+"/"+qrPnumber+"/"+qrAddress+"/"+qrDate, BarcodeFormat.QR_CODE,375,237);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                BarcodeEncoder encoder=new BarcodeEncoder();
                Bitmap ffinal=encoder.createBitmap(matrix);


                qr.setImageBitmap(ffinal);
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
        super.onBackPressed();
    }
}

