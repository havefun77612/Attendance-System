package com.havefun.attendancesystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    TextView nameText1, nameText2, mobileText1, mobileText2, emailText1, emailText2, addressText1, addressText2, dateText1, dateText2, userName, userEmail;
    ImageView profile_image;
    FirebaseUser user;
    List<UserInfo> UserInfoList;
    Button back_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeVars();
        addListners();
        checkDataSource();

    }



    /*
    ::::::::::::::::::::::::::::::::::::: Fetching User Data::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    private void checkDataSource() {
        if (isOnline()) {
            getNormalUserInfo();
        } else
            FancyToast.makeText(getApplicationContext(), "Check your internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
    }

    public void getNormalUserInfo() {
        if (user != null) {
            Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserId")
                    .equalTo(user.getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //UserInfoList.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            final UserInfo user = new UserInfo();
                            user.setUserAddress(data.child("UserAddress").getValue().toString());
                            user.setUserCompleteInfo(data.child("UserCompleteInfo").getValue().toString());
                            user.setUserEmail(data.child("UserEmail").getValue().toString());
                            user.setUserId(data.child("UserId").getValue().toString());
                            user.setUserName(data.child("UserName").getValue().toString());
                            user.setUserPhoneNumber(data.child("UserPhoneNumber").getValue().toString());
                          //  user.setDateofBirth(data.child("DateOfBith").getValue().toString());
                            UserInfoList.add(user);
                        }
                        Log.i("Getting user data:", "succedded");
                        FancyToast.makeText(getApplicationContext(), "Data fetched", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        getUserImage();
                        // Toast.makeText(getApplicationContext(), UserInfoList.get(0).toString(), Toast.LENGTH_LONG).show();


                    } else {
                        FancyToast.makeText(getApplicationContext(), "Sorry Running into Problem Right Now", FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                        Log.i("Getting user data:", "Failed No Users Founded");

                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i("Getting user data:", "Failed Error ");
                    FancyToast.makeText(getApplicationContext(),"Failed Getting User Data",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                }
            });
        } else {
            FancyToast.makeText(getApplicationContext(), "No Logged User", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
        }
    }

    private void getUserImage() {
        if (Boolean.parseBoolean(UserInfoList.get(0).getUserCompleteInfo())) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://attendance-system-29656.appspot.com/userImages/" + UserInfoList.get(0).getUserId() + ".jpg");
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.i("Mainprofile:::", "The Uri=  " + uri.toString());
                        setUri(uri.toString());

                }
            });
        } else {
            try {
                setUri(user.getPhotoUrl().toString());
            }catch (Exception e)
            {
                updateUiComponent();
                Log.i("null photo url", e.getMessage());
            }

        }
    }

    private void setUri(String toString) {
        Log.i("Setting Uri :", toString);
        UserInfoList.get(0).setUserProfileUri(toString);
        Log.i("Mainprofile::::", "setting uri to UserInfoList is " + UserInfoList.get(0).UserProfileUri);

        updateUiComponent();
    }
/*
::::::::::::::::::::::::::::::: Setting Up The User Information :::::::::::::::::::::::::::::::::::::::
 */
    private void updateUiComponent() {
        if(UserInfoList.get(0).getUserId()!=null&&!UserInfoList.get(0).getUserId().isEmpty()) {
            nameText2.setText(UserInfoList.get(0).getUserName());
            userName.setText(UserInfoList.get(0).getUserName());
            emailText2.setText(UserInfoList.get(0).getUserEmail());
            userEmail.setText(UserInfoList.get(0).getUserEmail());
            mobileText2.setText(UserInfoList.get(0).getUserPhoneNumber());
            addressText2.setText(UserInfoList.get(0).getUserAddress());
           // dateText2.setText(UserInfoList.get(0).getDateofBirth());
            Picasso.get().load(UserInfoList.get(0).getUserProfileUri()).placeholder(R.drawable.profile5).into(profile_image);
        }else if (user!=null){
            nameText2.setText(user.getDisplayName());
            userName.setText(user.getDisplayName());
            emailText2.setText(user.getEmail());
            userEmail.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.profile5).into(profile_image);

        }else {
            Log.i("Profile Activity", "updateUiComponent: No User Exist ");
        }
        }

    /*
     **** Check The Device State OF internet Connection
     */
    private boolean isOnline() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /*
    /// Inintialization vars and objects Stage && Listner Stages
     */
    private void initializeVars() {
        nameText1 = (TextView) findViewById(R.id.nametext1);
        nameText2 = (TextView) findViewById(R.id.nametext2);
        mobileText1 = (TextView) findViewById(R.id.mobiletext1);
        mobileText2 = (TextView) findViewById(R.id.mobiletext2);
        emailText1 = (TextView) findViewById(R.id.emailtext1);
        emailText2 = (TextView) findViewById(R.id.emailtext2);
        addressText1 = (TextView) findViewById(R.id.addresstext1);
        addressText2 = (TextView) findViewById(R.id.addresstext2);
        dateText1 = (TextView) findViewById(R.id.datetext1);
        dateText2 = (TextView) findViewById(R.id.datetext2);
        userName = (TextView) findViewById(R.id.username);
        userEmail = (TextView) findViewById(R.id.useremail);
        profile_image = (ImageView) findViewById(R.id.userImage);
        back_to_home=(Button) findViewById(R.id.back_to_home);
        // firebase part
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserInfoList = new ArrayList<>();
    }

    private void addListners() {
        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainPage.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MainPage.class));
        finish();
    }
}
