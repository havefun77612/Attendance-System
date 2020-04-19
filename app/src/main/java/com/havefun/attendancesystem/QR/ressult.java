package com.havefun.attendancesystem.QR;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.havefun.attendancesystem.FirebaseClass.WriteToFirebase;
import com.havefun.attendancesystem.HelperClass.InternetStatus;
import com.havefun.attendancesystem.OfflineDB.DBManager;
import com.havefun.attendancesystem.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;

import static com.havefun.attendancesystem.QR.ScanCourse.currentCourseCode;
import static com.havefun.attendancesystem.QR.ScanCourse.selectedLevel;

public class ressult extends AppCompatActivity {
    private static final String TAG = "InternetConnection";
    static ArrayList<UserData> array = new ArrayList<UserData>();//arraylist holding objects of users
    //static ArrayList<String> array2 = new ArrayList<String>();
    ScanQr scanQr = new ScanQr();
    Button insert, retrieve, delete, checkdata;
    String edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning_result);
        initialState();
        prepareAdapter();
        intialvariables();
        intlisteners();
    }

    private void intialvariables() {
        delete = (Button) findViewById(R.id.delete);
        retrieve = (Button) findViewById(R.id.retrieve);
        insert = (Button) findViewById(R.id.insert);
        checkdata = (Button) findViewById(R.id.check_data);
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

        if (customAdapter.isEmpty() || customAdapter.getCount() <= 0) {
            FancyToast.makeText(getApplicationContext(), "Sorry no Student assigned...", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
        } else {
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
            if (!currentCourseCode.isEmpty() && !currentCourseCode.equals(" ")) {
                if (!selectedLevel.isEmpty() && !selectedLevel.equals(" "))
                    uploadSessionAttendance(currentCourseCode);
            } else
                FancyToast.makeText(getApplicationContext(), "No Course Code Selected", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

        } else {
            Log.i(TAG, "testNetwork: ");
        }

    }

    ///  Add data to the hashmap
    public void uploadSessionAttendance(String courseCode) {

        HashMap<String, String> hashMap = new HashMap<>();
        WriteToFirebase writeToFirebase = new WriteToFirebase(getApplicationContext(), this);
        for (int i = 0; i < ScanQr.scanData.size(); i++) {
            hashMap.put("StudentName", scanQr.scanData.get(i).getName());
            hashMap.put("StudentID", scanQr.scanData.get(i).getID());
            hashMap.put("StudentEmail", scanQr.scanData.get(i).getEmail());
            writeToFirebase.addNewAttendanceData(courseCode, String.valueOf(i + 1), hashMap);
        }
    }

    public void intlisteners() {

        // insert data to offline database from scan table
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBManager(getApplicationContext()).insertScanٍTable(ScanQr.scanData);
            }
        });

        // retrieve data from scan table
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBManager(getApplicationContext()).getScanTable();
            }
        });


        // delete all data from scan table
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DBManager(getApplicationContext()).deleteAllRecordScan();

            }
        });


        // check if is scan table is empty ?(if it is not empty  will return false else will return true  )
        checkdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(new DBManager(getApplicationContext()).isEmptyTableScan());

            }
        });
    }

}
    /*
    // insert data to offline database from scan table
    public void insertdata(){
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DBManager( getApplicationContext() ).insertScanٍTable( ScanQr.scanData );
            }
        });
    }
    // retrieve data from scan table
    public void retrievedata() {

        retrieve.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new DBManager( getApplicationContext() ).getScanTable();
                //    StyleableToast.makeText( mContext, "data is retrieved succefully ", R.style.retrieve ).show();

            }
        } );
    }
    // delete all data from scan table
    public void  deletedata(){
        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DBManager( getApplicationContext() ).deleteAllRecordScan();
                //   StyleableToast.makeText( mContext, "data is deleted succefully ", R.style.delete ).show();

            }
        });
    }
  // check if is scan table is empty ?(if it is not empty  will return false else will return true  )
    public void checktable() {
        checkdata.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println( new DBManager( getApplicationContext() ).isEmptyTableScan());

            }
        } );
    }


*/
