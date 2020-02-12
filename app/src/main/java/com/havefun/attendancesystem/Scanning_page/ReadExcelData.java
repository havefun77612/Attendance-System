package com.havefun.attendancesystem.Scanning_page;

import android.os.Bundle;
import android.util.Log;

import com.havefun.attendancesystem.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;

public class ReadExcelData extends AppCompatActivity {
    InputStream inputStream;

    String[] ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        inputStream = getResources().openRawResource(R.raw.student);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {



                ids=csvLine.split(",");
                try{

                    Log.e("Collumn 1 ",""+ids[0]) ;



                }catch (Exception e){
                    Log.e("Unknown fuck",e.toString());
                }
            }




        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }



    }
}
