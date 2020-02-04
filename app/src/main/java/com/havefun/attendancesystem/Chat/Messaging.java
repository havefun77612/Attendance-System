package com.havefun.attendancesystem.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.havefun.attendancesystem.ChatUser;
import com.havefun.attendancesystem.R;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;

public class Messaging extends AppCompatActivity {
    ImageView reciver_Image;
    TextView reciverUserName;
    Intent intent;
    String reciverID;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String id,uri,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
        initializeVars();
        getReciverData();
    }

    /*
     ****** Inintialzation stage of the messaging class vars
     */
    private void initializeVars() {
        reciver_Image =  findViewById(R.id.messageUserImage);
        reciverUserName =  findViewById(R.id.messageUserName);
        intent = getIntent();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


    private void getReciverData() {
        reciverID = intent.getStringExtra("userID");
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserId")
                .equalTo(reciverID);


     FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("UserId").equalTo(reciverID).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             if (dataSnapshot.exists()){
                 for (DataSnapshot data :dataSnapshot.getChildren()) {
                 uri=data.child("UserProfileUri").getValue().toString();
                 name=data.child("UserName").getValue().toString();
                 }
                 reciverUserName.setText(name);
                 getUserImage(reciverID);
                 //Toast.makeText(Messaging.this, "Done", Toast.LENGTH_SHORT).show();
             }else {
                 Toast.makeText(Messaging.this, "Error ", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });



    }


    // Download the image
    private void getUserImage(final String user) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" +user+ ".jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("Messaging::::::", "The Uri=  " + uri.toString());
                Picasso.get().load(uri.toString()).placeholder(R.drawable.profile5).into(reciver_Image);
                Log.i("User Photo Path", "onBindViewHolder: " + uri.toString());
            }
        });

    }
}