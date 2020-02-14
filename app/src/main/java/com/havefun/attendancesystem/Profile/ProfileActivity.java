package com.havefun.attendancesystem.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.havefun.attendancesystem.DBManager;
import com.havefun.attendancesystem.FireBaseStorage;
import com.havefun.attendancesystem.MainPage;
import com.havefun.attendancesystem.R;
import com.havefun.attendancesystem.UserInfo;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    final private String TAG="ProfileActivity";
    TextView nameText1, nameText2, mobileText1, mobileText2, emailText1, emailText2, addressText1, addressText2, dateText1, dateText2, userName, userEmail;
    ImageView profile_image;
    FirebaseUser user;
    ArrayList<UserInfo> UserInfoList;
    Button back_to_home;
    Bitmap bitmap;
    DBManager offlineDB;
    Animation Animate1;
    TextView accountinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeVars();
        addListners();
        checkDataAvailability();
        addanimation();
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
                            final  UserInfo user = new UserInfo();
                            user.setUserAddress(data.child("UserAddress").getValue().toString());
                            user.setUserCompleteInfo(Boolean.parseBoolean(data.child("UserCompleteInfo").getValue().toString()));
                            user.setUserEmail(data.child("UserEmail").getValue().toString());
                            user.setUserId(data.child("UserId").getValue().toString());
                            user.setUserName(data.child("UserName").getValue().toString());
                            user.setUserPhoneNumber(data.child("UserPhoneNumber").getValue().toString());
                            user.setDateOfBirth(data.child("UserDate").getValue().toString());
                            try {
                                user.setUserType(data.child("UserType").getValue().toString());
                            }catch (Exception e){
                                Log.i(TAG, "onDataChange: "+e);
                            }
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


    /*
image intent section
 */
    private void pickM() {
        Intent intt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intt.setType("image/*");
        startActivityForResult(intt, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickM();
                } else {
                    FancyToast.makeText(getApplicationContext(), "We Need Storage Permission", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
            break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {//the IF statement was WRONG
            Uri i = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), i);

                profile_image.setImageBitmap(bitmap);
               FireBaseStorage uploadUserImage=new FireBaseStorage(getApplicationContext(),this);
               uploadUserImage.uploadUserImage(bitmap,user.getUid());

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void setUri(String toString) {
        Log.i("Setting Uri :", toString);
        UserInfoList.get(0).setUserProfileUri(toString);
        Log.i("Mainprofile::::", "setting uri to UserInfoList is " + UserInfoList.get(0).getUserProfileUri());

        updateUiComponent();
    }
/*
::::::::::::::::::::::::::::::: Setting Up The User Information  if Data came from online source :::::::::::::::::::::::::::::::::::::::
 */
    private void updateUiComponent() {
        if(UserInfoList.get(0).getUserId()!=null&&!UserInfoList.get(0).getUserId().isEmpty()) {
            nameText2.setText(UserInfoList.get(0).getUserName());
            userName.setText(UserInfoList.get(0).getUserName());
            emailText2.setText(UserInfoList.get(0).getUserEmail());
            userEmail.setText(UserInfoList.get(0).getUserEmail());
            mobileText2.setText(UserInfoList.get(0).getUserPhoneNumber());
            addressText2.setText(UserInfoList.get(0).getUserAddress());
            dateText2.setText(UserInfoList.get(0).getDateOfBirth());
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

        try {
            if (offlineDB.isEmptyTableProf()){
                Bitmap bitmap = ((BitmapDrawable)profile_image.getDrawable()).getBitmap();
                offlineDB.insertProfileTable(UserInfoList,bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        }

        private void updateUiWithOfflineData(){
            if(UserInfoList.get(0).getUserId()!=null&&!UserInfoList.get(0).getUserId().isEmpty()) {
                nameText2.setText(UserInfoList.get(0).getUserName());
                userName.setText(UserInfoList.get(0).getUserName());
                emailText2.setText(UserInfoList.get(0).getUserEmail());
                userEmail.setText(UserInfoList.get(0).getUserEmail());
                mobileText2.setText(UserInfoList.get(0).getUserPhoneNumber());
                addressText2.setText(UserInfoList.get(0).getUserAddress());
                dateText2.setText(UserInfoList.get(0).getDateOfBirth());
                try {
                    profile_image.setImageBitmap(UserInfoList.get(0).getBitmap());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (user!=null){
                nameText2.setText(user.getDisplayName());
                userName.setText(user.getDisplayName());
                emailText2.setText(user.getEmail());
                userEmail.setText(user.getEmail());
                Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.profile5).into(profile_image);

            }else {
                Log.i("Profile Activity", "updateUiComponent: No User Exist ");
            }

            try {
                if (offlineDB.isEmptyTableProf()){
                    Bitmap bitmap = ((BitmapDrawable)profile_image.getDrawable()).getBitmap();
                    offlineDB.insertProfileTable(UserInfoList,bitmap);
                }
            }catch (Exception e){
                e.printStackTrace();
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
        accountinfo = (TextView)findViewById( R.id.accountinfo );
        // firebase part
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserInfoList = new ArrayList<>();
        offlineDB =new DBManager(getApplicationContext());

    }
    public void addanimation(){
//        Animate1 = AnimationUtils.loadAnimation( com.havefun.attendancesystem.Profile.ProfileActivity.this,R.anim.lefttoright );
//        back_to_home.startAnimation( Animate1 );
//        profile_image.startAnimation( Animate1 );
//        accountinfo.startAnimation( Animate1 );

    }
    private void addListners() {
        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
                finish();
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (user!=null){
                pickM();
            }
            }
        });
    }
    // Checking if the data exist offline or not
    private void checkDataAvailability() {
        if (offlineDB.isEmptyTableProf()){
            checkDataSource();
        }else {
        UserInfoList=offlineDB.getProfileTable();
        updateUiWithOfflineData();
            Toast.makeText(this, "Data Exist", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),MainPage.class));
        finish();
    }
}
