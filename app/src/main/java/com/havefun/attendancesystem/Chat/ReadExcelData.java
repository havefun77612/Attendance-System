package com.havefun.attendancesystem.Chat;

import android.os.Bundle;
import android.util.Log;

import com.havefun.attendancesystem.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class ReadExcelData  {
    InputStream inputStream;

    String[] ids;
    String URL;

    public ReadExcelData() {
        URL = "http://github.com/havefun77612/Attendance-System/blob/master/app/Student_lists.xlsx";
    }
    public void checkTheExcellSheet(){

        try {
            java.net.URL mURL = new URL(URL);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) mURL.openConnection();
            inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
            // getting network os exception error
        } catch (MalformedURLException e) {
            e.printStackTrace();        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String excelLine;
            while ((excelLine = reader.readLine()) != null) {



                ids=excelLine.split(",");
                try{

                    Log.e("Collumn 1 ",""+ids[0]) ;



                }catch (Exception e){
                    Log.e("Unknown ",e.toString());
                }
            }




        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading Excel file: "+ex);
        }

    }
}

