package com.havefun.attendancesystem;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

public class WriteToFirebase {
    // Write a message to the database
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
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


    /*
    ::::::: User Data Structuring Models
     */

    public void addNewUserinfo(String DateOfBith, String OwnerEmail, String OwnerID, String userName, String password, String phoneNumber) {
        String PhotoURi;
        String UserCompleteInfo;
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference UserImagesReference = firebaseStorage.getReference().child("userImages/" + firebaseUser.getUid() + ".jpg");
        if (firebaseUser.getPhotoUrl() == null || firebaseUser.getPhotoUrl().toString().equals("")) {
            PhotoURi = String.valueOf(UserImagesReference.getDownloadUrl());
            UserCompleteInfo="true";
        } else {
            PhotoURi = firebaseUser.getPhotoUrl().toString();
            UserCompleteInfo="false";
        }
        HashMap<String, String> usermaMap = new HashMap<String, String>();
        usermaMap.put("DateOfBith", DateOfBith);
        usermaMap.put("UserCompleteInfo", UserCompleteInfo);
        usermaMap.put("UserEmail", OwnerEmail);
        usermaMap.put("UserId", OwnerID);
        usermaMap.put("UserName", userName);
        usermaMap.put("UserPassword", password);
        usermaMap.put("UserPhoneNumber", phoneNumber);
        usermaMap.put("UserProfileUri", PhotoURi);

        myRef.push().setValue(usermaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FancyToast.makeText(context, "Your Data Updated successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                } else {
                    FancyToast.makeText(context, String.valueOf(task.getException()), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
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