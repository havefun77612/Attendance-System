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
import com.havefun.attendancesystem.UserInfo;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

public class Messaging extends AppCompatActivity {
    ImageView reciver_Image;
    TextView reciverUserName;
    Intent intent;
    String reciverID;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

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
        reciver_Image = (ImageView) findViewById(R.id.messageUserImage);
        reciverUserName = (TextView) findViewById(R.id.messageUserName);
        intent = getIntent();
        reciverID = intent.getStringExtra("userID");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


    private void getReciverData() {
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserId")
                .equalTo(reciverID);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ChatUser user = dataSnapshot.getValue(ChatUser.class);
                    assert user != null;
                    reciverUserName.setText(user.getUserName());
                    getUserImage(user);
                }else {
                    FancyToast.makeText(getApplicationContext(),"This user May not exist ",
                            FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // Download the image
    private void getUserImage(final ChatUser user) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" + user.getUserId() + ".jpg");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i("Mainprofile:::", "The Uri=  " + uri.toString());
                user.setUserProfileUri(uri.toString());
                Picasso.get().load(user.getUserProfileUri()).placeholder(R.drawable.profile5).into(reciver_Image);
                Log.i("User Photo Path", "onBindViewHolder: " + user.getUserProfileUri());
            }
        });

    }
}