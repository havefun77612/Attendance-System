package com.havefun.attendancesystem.QueryFirebase;

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
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ShowAttendace extends AppCompatActivity {
    Spinner coursesList, dateList;
    ListView attendanceList;
    ArrayAdapter coursesListArrayAdapter, dateListArrayAdapter, attendanceListArrayAdapter;
    ArrayList<String> attendanceArrayList;
    ArrayList<String> coursesOptions, dateOptions;
    public static String selectedCourse = "null";
    public static String selectedDate = "null";

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
        attendanceListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendanceArrayList);
        attendanceList.setAdapter(attendanceListArrayAdapter);
        if (new InternetStatus(getApplicationContext()).checkNetworkStatus()) {
            queryTheCourses();
        }


    }

    private void addListners() {
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
        dateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDate = parent.getItemAtPosition(position).toString();
                setDataToAttendanceListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

                    selectedCourse = coursesOptions.get(0);
                    addOptionsToCoursesList();
                    Log.i("dataConverted", "onDataChange: coursesOptions" + coursesOptions.toString());

                } else {
                    Toast.makeText(ShowAttendace.this, "Sorry No courses exists", Toast.LENGTH_SHORT).show();
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

   private void queryTheDates() {
       dateOptions.clear();
       Query query = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse);
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if (dataSnapshot.exists()) {
                   for (DataSnapshot data : dataSnapshot.getChildren()) {
                       if (!dateOptions.contains(data.getKey()))
                           dateOptions.add(data.getKey());
                       else Log.i("ShowAttendance", "onDataChange: dataExist");
                   }
                   selectedDate = dateOptions.get(0);
                   if (!dateOptions.contains("Semester Attendance"))
                       dateOptions.add("Semester Attendance");
                   addOptionsToDateList();
                   FancyToast.makeText(getApplicationContext(), "data Fetched Successfully",
                           FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                   Log.i("dataConverted", "onDataChange: coursesOptions" + dateOptions.toString());

               } else {
                   Toast.makeText(ShowAttendace.this, "Sorry No courses exists", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
   }

   private void addOptionsToDateList() {
       dateListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateOptions);
       dateListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       dateList.setAdapter(dateListArrayAdapter);
       setDataToAttendanceListView();
   }

  private void setDataToAttendanceListView() {
      attendanceArrayList.clear();
      DatabaseReference reference;
      if (selectedDate.equals("Semester Attendance")) {
          reference = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse + "/");
          reference.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  if (!selectedDate.equals("null")) {
                      for (DataSnapshot data : dataSnapshot.getChildren()) {
                          // AttendanceModel value = dataSnapshot.getValue(AttendanceModel.class);
                          String formatedDataForNameAndID = data.child("StudentName").getValue().toString() + "\n" + data.child("StudentID").getValue().toString();
                              attendanceArrayList.add(formatedDataForNameAndID);
                          attendanceListArrayAdapter.notifyDataSetChanged();
                          Log.i("dataContent", "onChildAdded: " + dataSnapshot.getChildren() + "   " + data.getValue());
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

              }
          });

      } else {
          reference = FirebaseDatabase.getInstance().getReference().child("Attendance/" + selectedCourse + "/" + selectedDate);
          reference.addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  if (!selectedDate.equals("null")) {
                      if (dataSnapshot.exists()) {
                          AttendanceModel value = dataSnapshot.getValue(AttendanceModel.class);
                          String formatedDataForNameAndID = value.getStudentName() + "\n" + value.getStudentID();
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

               }
           });


       }
   }
}
