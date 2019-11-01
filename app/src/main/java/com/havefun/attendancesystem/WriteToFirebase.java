package com.havefun.attendancesystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

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
    private String TAG="Write To FireBase ";
    private  HashMap<String, String> usermaMap;
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

    public void addNewUserinfo(HashMap<String,String> userData, final Bitmap profileImage, String UserCompleteInfo) {
        setReferance("Users");
        String PhotoURi;
        usermaMap = new HashMap<String, String>();
        usermaMap=userData;
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
                    if (profileImage!=null){
                        FireBaseStorage firebaseStorage1=new FireBaseStorage(context,appCompatActivity);
                        firebaseStorage1.uploadUserImage(profileImage,usermaMap.get("UserId"));
                    }
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