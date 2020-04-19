package com.havefun.attendancesystem.QueryFirebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.havefun.attendancesystem.HelperClass.InternetStatus;
import com.havefun.attendancesystem.R;
//import com.havefun.attendancesystem.mail.CustomAdapter2;
//import com.havefun.attendancesystem.mail.mail;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShowAttendace extends AppCompatActivity {
    Spinner coursesList, dateList;
    ListView attendanceList;
    ArrayAdapter coursesListArrayAdapter, dateListArrayAdapter, attendanceListArrayAdapter;
    ArrayList<String> attendanceArrayList;
    public static ArrayList<AttendanceModel> listData;
    ArrayList<String> coursesOptions, dateOptions;
    String selectedCourse = "null";
    String selectedDate = "null";
    int counter = -1;
    final String mes=",You have exceeded your absence days";
    final Intent intent=new Intent(Intent.ACTION_SEND);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_attendace);
        initialVars();
        addListners();


    }


    private void initialVars() {
        coursesList = (Spinner) findViewById(R.id.coursesLists);
        dateList = (Spinner) findViewById(R.id.dateList);
        attendanceList = (ListView) findViewById(R.id.attendanceList);
        attendanceArrayList = new ArrayList<>();
        coursesOptions = new ArrayList<>();
        dateOptions = new ArrayList<>();
        listData = new ArrayList<>();

        attendanceListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendanceArrayList);
        //CustomAdapter2 adapter2=new CustomAdapter2(this,listData);
        attendanceList.setAdapter(attendanceListArrayAdapter);
        if (new InternetStatus(getApplicationContext()).checkNetworkStatus()) {
            queryTheCourses();
        }


    }

    private void addListners() {
        // listner for first spinner Courses
        coursesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourse = parent.getItemAtPosition(position).toString();
                queryTheDates();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // email listener
        attendanceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowAttendace.this);
                builder.setTitle("Send email:");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{listData.get(position).getStudentEmail()});
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Your Absence");
                        intent.putExtra(Intent.EXTRA_TEXT,"Dear "+listData.get(position).getStudentName()+" "+mes);
                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent,"Choose Mail App"));
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

        /// listener for the second spinner  date
        dateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate = parent.getItemAtPosition(position).toString();
                try {
                    setDataToAttendanceListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /*
    |||||| this query made for getting the available courses with attendance
     */
    private void queryTheCourses() {
        coursesOptions.clear();
        Query query = FirebaseDatabase.getInstance().getReference().child("Attendance");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(ShowAttendace.this, "data exist ", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        coursesOptions.add(data.getKey());
                    }
                    if (coursesOptions.size()>0){
                       // selectedCourse = coursesOptions.get(0);
                        addOptionsToCoursesList();}
                    Log.i("dataConverted", "onDataChange: coursesOptions" + coursesOptions.toString());

                } else {
                  /*
                  *** This part was having the crashin problem of gettin 0 index of couseslist array
                   */
                    //  Toast.makeText(ShowAttendace.this, "Sorry No courses exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addOptionsToCoursesList() {
        coursesListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coursesOptions);
        coursesListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coursesList.setAdapter(coursesListArrayAdapter);

        queryTheDates();
    }

    // this query for getting the available dates for each course selected
    private void queryTheDates() {
        dateOptions.clear();
        Query query = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (!dateOptions.contains(data.getKey()))
                                dateOptions.add(data.getKey());
                            else Log.i("ShowAttendance", "onDataChange: dataExist");
                        }
                        if (dateOptions.size()>0){
                           // selectedDate = dateOptions.get(0);
                            if (!dateOptions.contains("Semester Attendance"))
                                dateOptions.add("Semester Attendance");
                            addOptionsToDateList();
                            FancyToast.makeText(getApplicationContext(), "data Fetched Successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();}
                        Log.i("dataConverted", "onDataChange: coursesOptions" + dateOptions.toString());
                    } else {
                        Toast.makeText(ShowAttendace.this, "Sorry No courses exists", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println("You can find error here :"+e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addOptionsToDateList() {
        try {
            dateListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateOptions);
            dateListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dateList.setAdapter(dateListArrayAdapter);
            setDataToAttendanceListView();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /*
     *** this query to get attendance for selected date inside selected course
     */

    private void setDataToAttendanceListView()  {
        attendanceArrayList.clear();
        listData.clear();
        DatabaseReference reference;
        //  setting referance to get all the data of the course
        if (selectedDate.equals("Semester Attendance")) {
            reference = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse + "/");
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (!selectedDate.equals("null")) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            // AttendanceModel value = dataSnapshot.getValue(AttendanceModel.class);
                            // setting Data of the snapshoot to model
                            AttendanceModel model = new AttendanceModel();
                            model.setStudentID(data.child("StudentID").getValue().toString());
                            model.setStudentName(data.child("StudentName").getValue().toString());
                            model.setStudentEmail(data.child("StudentEmail").getValue().toString());
                            listData.add(model);

                            String formatedDataForNameAndID = data.child("StudentName").getValue().toString() + "\n"
                                    + data.child("StudentID").getValue().toString() + "\n"
                                    + data.child("StudentEmail").getValue().toString();
                            // if (!attendanceArrayList.contains(formatedDataForNameAndID))
                            attendanceArrayList.add(formatedDataForNameAndID);
                            Log.i("dataContent", "onChildAdded: " + dataSnapshot.getChildren() + "   " + data.getValue());
                        }

                        counter++;
                        if (counter >= dataSnapshot.getChildrenCount()) {
                            countAttendance();
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), "Sorry No Attendance To This course appear yet", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("You can find error here :"+databaseError.getMessage());

                }
            });

        } else {
            // setting refrance to get a pacific date attendance
            reference = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse + "/" + selectedDate);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (!selectedDate.equals("null")) {
                        if (dataSnapshot.exists()) {
                            AttendanceModel value = dataSnapshot.getValue(AttendanceModel.class);
                            listData.add(value);
                            String formatedDataForNameAndID = value.getStudentName() + "\n" + value.getStudentID() + "\n" + value.getStudentEmail();
                            if (!attendanceArrayList.contains(formatedDataForNameAndID))
                                attendanceArrayList.add(formatedDataForNameAndID);
                            attendanceListArrayAdapter.notifyDataSetChanged();
                            Log.i("dataContent", "onChildAdded: " + dataSnapshot.getChildren() + "   " + value.getStudentID());
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), "Sorry No Attendance To This course appear yet", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("You can find error here :"+databaseError.getMessage());
                }
            });


        }
    }

    private void countAttendance() {

        ArrayList<String> temp_array2 = new ArrayList<>(attendanceArrayList);
        attendanceArrayList.clear();
        Set<String> set = new HashSet<>(temp_array2);

        ArrayList<String> temp_array = new ArrayList<>(set);
        for (int i = 0; i < temp_array.size(); i++) {
            Log.e(temp_array.get(i), "\nAttend: " + Collections.frequency(temp_array2, temp_array.get(i)) + " out of " + (dateList.getCount() - 1) + " lecture");
            attendanceArrayList.add(temp_array.get(i) + "\nAttend: " + Collections.frequency(temp_array2, temp_array.get(i)) + " out of " + (dateList.getCount() - 1) + " lecture");
        }

        attendanceListArrayAdapter.notifyDataSetChanged();
        counter = -1;


    }


    @Override
    public void onBackPressed() {
        finish();
        attendanceArrayList.clear();
        dateOptions.clear();
        //listData.clear();
        coursesOptions.clear();
        super.onBackPressed();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            Runtime.getRuntime().gc();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}