package com.havefun.attendancesystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class CompleteLogin extends AppCompatActivity {
    Button submit;
    EditText username, email, phone, address, dateofbirth;
    Animation animate1, animate2;
    ImageView image_profile;
    final int imagePcode = 50;
    final int prCode = 51;//must be final for onRequestPermissionsResult
    Bitmap bitmap=null;
    //end of image vals
    final HashMap<String, String> hash = new HashMap<String, String>();
    FirebaseUser firebaseUser;
    String TAG = "CompleteLogin Page", UserCompleteInfo = "false";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_login);
        initializeVars();
        setAvailableData();
        setListners();
        addingAnimation();


    }




    /*
    :::::::: Validate the data section
     */

    public boolean userOK() {
        String user = username.getText().toString();
        String pat = "^[a-zA-Z]*$";
        if (user.matches(pat)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean phoneOk() {
        String patt = "^01[0,1,2,5]{1}[0-9]{8}";
        String phoon = phone.getText().toString();
        if (phoon.matches(patt)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean emailOK() {
        String pattt = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String emailf = email.getText().toString();
        if (emailf.matches(pattt)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean allOk() {
        if (emailOK() && userOK() && phoneOk()) {
            return true;
        } else {
            return false;
        }
    }


    /*
  image intent section
   */
    private void pickM() {
        Intent intt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intt.setType("image/*");
        startActivityForResult(intt, imagePcode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case prCode: {
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
        if (requestCode == imagePcode && resultCode == RESULT_OK && null != data) {//the IF statement was WRONG
            Uri i = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), i);

                image_profile.setImageBitmap(bitmap);
                UserCompleteInfo = "true";

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    /*
    ::: Try To Fetch Existing Data
     */
    private void setAvailableData() {
        try {
            username.setText(firebaseUser.getDisplayName());
            email.setText(firebaseUser.getEmail());
            email.setEnabled(false);
            Picasso.get().load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.profile5).into(image_profile);


        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    /*
    :::: initializing stage
     */
    private void initializeVars() {
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        dateofbirth = (EditText) findViewById(R.id.dateofbirth);
        submit = (Button) findViewById(R.id.submit);
        image_profile = (ImageView) findViewById(R.id.profileImage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);


    }

    private void addingAnimation() {

        animate1 = AnimationUtils.loadAnimation(CompleteLogin.this, R.anim.righttoleft);
        username.startAnimation(animate1);
        email.startAnimation(animate1);
        phone.startAnimation(animate1);
        address.startAnimation(animate1);
        dateofbirth.startAnimation(animate1);

    }

    private void setListners() {

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(per, prCode);
                    } else {
                        pickM();
                    }
                } else {
                    pickM();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Animation animate = AnimationUtils.loadAnimation(CompleteLogin.this, R.anim.fadein);
                submit.startAnimation(animate);
                String user = username.getText().toString();
                String phoon = phone.getText().toString();
                String emailf = email.getText().toString();
                String userAddress = address.getText().toString();
                if (!userOK()) {
                    username.setError("please check your user name");
                }
                if (!emailOK()) {
                    email.setError("please check your email");
                }
                if (!phoneOk()) {
                    phone.setError("please check your phone number");
                }
                if (allOk()) {
                    disableControllers();
                    hash.put("UserName", user);
                    hash.put("UserPhoneNumber", phoon);
                    hash.put("UserEmail", emailf);
                    hash.put("UserId", firebaseUser.getUid());
                    hash.put("UserAddress", userAddress);
                    if (firebaseUser.getPhotoUrl() != null && !firebaseUser.getPhotoUrl().toString().equals("")) {
                        hash.put("UserProfileUri", firebaseUser.getPhotoUrl().toString());
                    } else {
                        hash.put("UserProfileUri", "");
                    }
                    WriteToFirebase writeToFirebase = new WriteToFirebase(getApplicationContext(), CompleteLogin.this);
                    writeToFirebase.addNewUserinfo(hash, bitmap, UserCompleteInfo);

                }

            }
        });


    }

    private void disableControllers() {
        image_profile.setEnabled(false);
        submit.setEnabled(false);
        username.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
        dateofbirth.setEnabled(false);
    }

}
