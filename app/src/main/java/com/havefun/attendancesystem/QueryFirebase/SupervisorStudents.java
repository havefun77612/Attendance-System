package com.havefun.attendancesystem.QueryFirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.havefun.attendancesystem.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class SupervisorStudents extends AppCompatActivity {
    Spinner supervisorList;
    ListView studentsList;
    ArrayAdapter arrayAdapter;
    ArrayList<String> students;
   final String[] supervisorOptions={"Dr.Yasser Fouaad Ramadan","Dr.Hussein Mohammed Sharaf",
            "Dr. Mohamed  Ali Atia","Dr. Fayza Ahmed Elsayed","Dr. Samah Ahmed Zaki",
            "Dr.Haitham Farook Abd Elfattah","Dr.Wael Mohammed Fawaz"};
    String selected="null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_students);
        initialVars();
        addListners();
    }



    private void initialVars() {
    supervisorList=findViewById(R.id.supervisorList);
    studentsList=findViewById(R.id.studentsList);
    students=new ArrayList<>();
    // list view adapter
    arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,students);
    studentsList.setAdapter(arrayAdapter);
    // spinner adapter
        ArrayAdapter spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,supervisorOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisorList.setAdapter(spinnerAdapter);

    }

    private void addListners() {
    supervisorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           selected= parent.getItemAtPosition(position).toString();
           if (!selected.isEmpty()&&!selected.equals("null")){
               queryData();
           }else {
               Toast.makeText(SupervisorStudents.this, "Please select the supervisor", Toast.LENGTH_SHORT).show();
           }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    }


    private void queryData(){
        students.clear();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Student_2020");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    StudentModel value = dataSnapshot.getValue(StudentModel.class);
                    if (selected.equals(value.getSupervisor())){
                    String formatedDataForNameAndID = value.getName() + "\n" + value.getID();
                    students.add(formatedDataForNameAndID);
                    arrayAdapter.notifyDataSetChanged();
                    }
                }else {
                    FancyToast.makeText(getApplicationContext(),"No Data Exist right now ",FancyToast.LENGTH_LONG
                    ,FancyToast.ERROR,true).show();
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
