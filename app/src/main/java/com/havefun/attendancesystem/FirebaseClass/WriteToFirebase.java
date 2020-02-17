package com.havefun.attendancesystem.FirebaseClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.havefun.attendancesystem.Profile.ProfileActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

public class WriteToFirebase {
    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    private String TAG = "Write To FireBase ";
    private HashMap<String, String> usermaMap;
    private HashMap<String, String> attendStudentForCourse;
    /*
     ** Needed Data
     */
    private String userName, password, phoneNumber, DateOfBith, OwnerID, OwnerEmail, CreationDate;
    private Context context;
    private AppCompatActivity appCompatActivity;

    public WriteToFirebase(Context context, AppCompatActivity appCompatActivity) {
        database = FirebaseDatabase.getInstance();
        this.context = context;
        this.appCompatActivity = appCompatActivity;
    }

    public WriteToFirebase(Context context) {
        this.context = context;
    }


    /*
    ::::::: User Data Structuring Models
     */

    public void addNewUserinfo(HashMap<String, String> userData, final Bitmap profileImage, String UserCompleteInfo) {
        setReferance("Users");
        String PhotoURi;
        usermaMap = new HashMap<String, String>();
        usermaMap = userData;
        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference UserImagesReference = firebaseStorage.getReference().child("userImages/" + usermaMap.get("UserId") + ".jpg");
        if (UserCompleteInfo.equals("true")) {
            PhotoURi = String.valueOf(UserImagesReference.getDownloadUrl());
        } else {
            PhotoURi = usermaMap.get("UserProfileUri");
        }

        usermaMap.put("UserCompleteInfo", UserCompleteInfo);
        usermaMap.put("UserProfileUri", PhotoURi);

        myRef.push().setValue(usermaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Your Data Updated successfully");
                    try {
                        int c = profileImage.getWidth();
                        if (profileImage != null) {
                            FireBaseStorage firebaseStorage1 = new FireBaseStorage(context, appCompatActivity);
                            firebaseStorage1.uploadUserImage(profileImage, usermaMap.get("UserId"));
                        }
                    } catch (Exception w) {
                        appCompatActivity.finish();
                        appCompatActivity.startActivity(new Intent(context, ProfileActivity.class));
                    }

                } else {
                    FancyToast.makeText(context, String.valueOf(task.getException()), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
        });

    }

    /*
    //// The Attendance Part
     */
    public void addnewAttendanceToCourse(final String CourseCode, HashMap<String, String> data) {
        setReferance(CourseCode);
        myRef.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "onComplete: Successfully uplaod course " + CourseCode + " Attendance");
                } else {
                    Log.i(TAG, "onComplete: Failed");
                }

            }
        });

    }

    /*
     **** add attendance info for spacific courses
     */
    public void addNewAttendanceData(String courseCode, HashMap<String, String> attendUsersInCourse) {
        setReferance(courseCode);
        attendStudentForCourse = new HashMap<>();
        attendStudentForCourse = attendUsersInCourse;
        myRef.push().setValue(attendStudentForCourse).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FancyToast.makeText(context, "Course attendance saved sccuffully", FancyToast.LENGTH_LONG, FancyToast
                            .SUCCESS, true).show();
                } else {
                    FancyToast.makeText(context, "Course attendance not saved", FancyToast.LENGTH_LONG, FancyToast
                            .ERROR, true).show();
                    FancyToast.makeText(context, "we'll try again when internet exist", FancyToast.LENGTH_LONG, FancyToast
                            .INFO, true).show();
                }
            }
        });
    }

    /*
    ::::::: update existing user
     */
    public void updateUserCompleteInfo(String key, Boolean status) {
        setReferance("Users");
        myRef.child(key).child("UserCompleteInfo").setValue(String.valueOf(status)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FancyToast.makeText(context, "updated Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
            }

        });
    }

    /*
     *** Adding faculity  Student list to DB
     */

    public void addAllStudentList(HashMap<String, String> data, String number) {
        setReferance("Student_2020");
        myRef.child(number).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Data uploaded success ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed Uploading Data ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
     ** Add new Course to available courses
     */
    public void addNewCourse(HashMap<String, String> data) {
        setReferance("Courses");
        myRef.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                FancyToast.makeText(context, "تم اضافة ال  كورس بنجاح " ,FancyToast.LENGTH_LONG
                ,FancyToast.SUCCESS,true).show();
            }else {
                FancyToast.makeText(context, "حدث خطأ اثناء الاضافة برجاء التأكد من اتصال الانترنت" ,FancyToast.LENGTH_LONG
                        ,FancyToast.ERROR,true).show();
            }
            }
        });
    }

    /*
     ** Method used to determinde the instance of the firebase = Determined the root
     */
    private void setReferance(String root) {
        try {
            if (root.isEmpty()) {
                myRef = FirebaseDatabase.getInstance().getReference();
            } else {
                myRef = FirebaseDatabase.getInstance().getReference().child(root);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}