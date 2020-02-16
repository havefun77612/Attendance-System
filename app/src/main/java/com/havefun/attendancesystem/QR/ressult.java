package com.havefun.attendancesystem.QR;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.havefun.attendancesystem.DBManager;
import com.havefun.attendancesystem.InternetStatus;
import com.havefun.attendancesystem.R;
import com.havefun.attendancesystem.WriteToFirebase;

import java.util.ArrayList;
import java.util.HashMap;

public class ressult extends AppCompatActivity {
    private static final String TAG = "InternetConnection";
    static ArrayList<UserData> array = new ArrayList<UserData>();//arraylist holding objects of users
    //static ArrayList<String> array2 = new ArrayList<String>();
    ScanQr scanQr = new ScanQr();


    String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_result);
        initialState();
        prepareAdapter();

    }

    private void prepareAdapter() {
        //createSTR(array);
        final CustomAdapter customAdapter = new CustomAdapter(this, scanQr.scanData);
        final CustomAdapter customAdapter2; //= new CustomAdapter(this, array2);
        final ListView listView = (ListView) findViewById(R.id.list);
        if (array == null) {
            Toast.makeText(getApplicationContext(), "No data exist", Toast.LENGTH_LONG).show();
        } else {
            int x = array.size();
            String v = Integer.toString(x);
            // Toast.makeText(getApplicationContext(), "notnull" + v, Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //array2.set(position,"hi");
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ressult.this);
                builder.setTitle("Change The Name:");


                final EditText input = new EditText(ressult.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
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
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ressult.this);
                builder.setTitle("Title");


                final TextView del = new TextView(ressult.this);
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

    private void initialState() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("qrRess");

    }


    // CHECK THE NETWORK STATUS AND DECIDE EVEN TO UPLOAD THE DATA OR TO STORE IT TILL UPLOAD
    public void testNetwork(View view) {

        InternetStatus internetStatus = new InternetStatus(getApplicationContext());
        if (internetStatus.checkNetworkStatus()) {
            Log.i(TAG, "testNetwork: ");
            uploadSessionAttendance();

        } else {
            Log.i(TAG, "testNetwork: ");
        }

    }

    ///  Add data to the hashmap
    public void uploadSessionAttendance() {
        HashMap<String, String> hashMap= new HashMap<>();
        WriteToFirebase writeToFirebase = new WriteToFirebase(getApplicationContext(), this);
        for (int i = 0; i < ScanQr.scanData.size(); i++) {
            hashMap.put("StudentName", scanQr.scanData.get(i).getName());
            hashMap.put("StudentID", scanQr.scanData.get(i).getID());
            writeToFirebase.addNewAttendanceData("Cs233", hashMap);
        }


    }
}