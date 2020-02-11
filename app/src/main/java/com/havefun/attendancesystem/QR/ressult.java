package com.havefun.attendancesystem.QR;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.havefun.attendancesystem.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ressult extends AppCompatActivity {
    static ArrayList<UserData> array = new ArrayList<UserData>();//arraylist holding objects of users
    //static ArrayList<String> array2 = new ArrayList<String>();
    ScanQr scanQr=new ScanQr();


    String edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_result);
        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra("qrRess");

        //array=scanQr.scanData;


        // array = (ArrayList<UserData>) bundle.getSerializable("extra");
        //array =bundle.getParcelableArrayList("qrResults");




        //createSTR(array);
        final CustomAdapter customAdapter = new CustomAdapter(this,scanQr.scanData);
        final CustomAdapter customAdapter2; //= new CustomAdapter(this, array2);
        final ListView listView = (ListView) findViewById(R.id.list);
        if(array==null){
            Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
        }else {int x=array.size();
            String v=Integer.toString(x);
            Toast.makeText(getApplicationContext(),"notnull"+v,Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //array2.set(position,"hi");
                final int pos=position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ressult.this);
                builder.setTitle("Title");


                final EditText input = new EditText(ressult.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit = input.getText().toString();
                        //customAdapter.arrayList.get(pos).setName(edit);
                        scanQr.scanData.get(pos).setName(edit);

                        listView.setAdapter(customAdapter);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();




            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos=position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ressult.this);
                builder.setTitle("Title");


                final TextView del=new TextView(ressult.this);
                del.setText("do you want to delete the item?");
                builder.setView(del);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //customAdapter.arrayList.remove(pos);
                        scanQr.scanData.remove(pos);
                        //scanQr.del(pos);
                        listView.setAdapter(customAdapter);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            }
        });





    }


}