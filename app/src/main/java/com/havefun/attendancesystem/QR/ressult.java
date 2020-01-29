package com.havefun.attendancesystem.QR;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.havefun.attendancesystem.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ressult extends AppCompatActivity {
    static ArrayList<String> array = new ArrayList<String>();
    static ArrayList<String> array2 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_result);
        Bundle bundle = getIntent().getExtras();
        array = bundle.getStringArrayList("qrResults");
        createSTR(array);
        CustomAdapter customAdapter = new CustomAdapter(this, array2);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(customAdapter);




    }

    public void createSTR(ArrayList<String>x){
        String temp[];
        String finall;
        for(int i=0;i<x.size();i++){
            temp=array.get(i).split("/");
            finall="Name:"+temp[0]+"\nID:"+temp[1];
            array2.add(finall);
        }

    }
}