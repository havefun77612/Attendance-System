package com.havefun.attendancesystem;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class FireBaseStorage {
    FirebaseStorage storage ;
    StorageReference storageRef;
    StorageReference ImageReference;
    String fileName;
    String path;
    String parent;
    Context context;
    AppCompatActivity appCompatActivity;
    FireBaseStorage(Context context,AppCompatActivity appCompatActivity){
        storage = FirebaseStorage.getInstance();
        storageRef=  storage.getReference();
        this.appCompatActivity=appCompatActivity;
        this.context=context;
    }

     void  uploadUserImage(Bitmap bitmap,String fileName){
        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(fileName+".jpg");
        setReferance("userImages");
// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("userImages/"+fileName+".jpg");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("Upload Task Failure: ",exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                FancyToast.makeText(context,"Image Uploaded ^__^",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                appCompatActivity.findViewById(R.id.progressBar2).setVisibility(View.GONE);
                appCompatActivity.finish();
                appCompatActivity.startActivity(new Intent(context,ProfileActivity.class));
            }
        });

    }





    /*
     ** Method used to determinde the instance of the firebase = Determined the root
     */
    private void setReferance(String root) {
        try {
            if (root.isEmpty()) {
                ImageReference=storageRef;
            } else {
                ImageReference=storageRef.child(root);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public String getFileName() {
        fileName=ImageReference.getName();
        return fileName;
    }

    public String getPath() {
        path=ImageReference.getPath();
        return path;
    }

    public String getParent() {
        parent= String.valueOf(ImageReference.getParent());
        return parent;
    }


}
