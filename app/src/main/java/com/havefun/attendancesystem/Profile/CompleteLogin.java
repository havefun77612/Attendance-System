package com.havefun.attendancesystem.Profile;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.havefun.attendancesystem.HelperClass.InternetStatus;
import com.havefun.attendancesystem.R;
import com.havefun.attendancesystem.FirebaseClass.WriteToFirebase;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class CompleteLogin extends AppCompatActivity {
    Button submit, date;
    EditText username, email, phone, address, dateofbirth,passwordForUserType;
    Animation animate1, animate2;
    ImageView image_profile;
    Switch checkTheUserType;
    FirebaseAuth mAuth;
    LinearLayout completLoginMainLayout;
    final int imagePcode = 50;
    final int prCode = 51;//must be final for onRequestPermissionsResult
    Bitmap bitmap = null;
    //end of image vals

    final HashMap<String, String> hash = new HashMap<String, String>();
    FirebaseUser firebaseUser;
    String TAG = "CompleteLogin Page", UserCompleteInfo = "false";
    ProgressBar progressBar;
    String userDate, userType;
    // intent for checking the source if the admin is the one how will create this account for a new admin or for doctor

    Boolean fromAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.complete_login);
        initializeVars();
        setAvailableData();
        setListners();
  //      addingAnimation();


    }




    /*
    :::::::: Validate the data section
     */

    public boolean userOK() {
        String user = username.getText().toString();
        String pat = "^[a-zA-Z ]*$";
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

    public boolean dateOK() {
        if (userDate != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean allOk() {
        if (emailOK() && userOK() && phoneOk() && dateOK()) {
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
        if (fromAdmin) {
            userType = "Doctor";

        } else {
            userType = "Student";
            completLoginMainLayout.removeView(passwordForUserType);
            completLoginMainLayout.removeView(checkTheUserType);
            try {
                username.setText(firebaseUser.getDisplayName());
                email.setText(firebaseUser.getEmail());
                email.setEnabled(false);
                Picasso.get().load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.profile5).into(image_profile);


            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
            }
        }
    }

    /*
    :::: initializing stage
     */
    private void initializeVars() {
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        fromAdmin = getIntent().getBooleanExtra("fromAdmin", false);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        dateofbirth = (EditText) findViewById(R.id.dateofbirth);
        checkTheUserType = findViewById(R.id.chooseType);
        passwordForUserType=findViewById(R.id.passwordForUserType);
        mAuth = FirebaseAuth.getInstance();
        completLoginMainLayout=findViewById(R.id.completLoginMainLayout);
        dateofbirth.setEnabled(false);//to stop the user from adding something to the date
        submit = (Button) findViewById(R.id.submit);
        image_profile = (ImageView) findViewById(R.id.profileImage);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        date = findViewById(R.id.date);


    }
/*
    private void addingAnimation() {
        animate2 = AnimationUtils.loadAnimation( CompleteLogin.this, R.anim.bounce );
        animate1 = AnimationUtils.loadAnimation(CompleteLogin.this, R.anim.zoomin);

    }
*/
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

                String user = username.getText().toString();
                String phoon = phone.getText().toString();
                String emailf = email.getText().toString();
                String userAddress = address.getText().toString();
                if (!userOK()) {
                    username.setError("please check your user name");
                    disableControllers();
                }
                if (!emailOK()) {
                    email.setError("please check your email");
                    disableControllers();
                }
                if (!phoneOk()) {
                    phone.setError("please check your phone number");
                    disableControllers();
                }
                if (!dateOK()) {
                    dateofbirth.setError("please set a date");
                    disableControllers();
                }
                if (allOk()) {
                    disableControllers();
                    hash.put("UserName", user);
                    hash.put("UserPhoneNumber", phoon);
                    hash.put("UserEmail", emailf);
                    hash.put("UserId", firebaseUser.getUid());
                    hash.put("UserAddress", userAddress);
                    hash.put("UserDate", userDate);
                    hash.put("UserType", userType);

                    if (firebaseUser.getPhotoUrl() != null && !firebaseUser.getPhotoUrl().toString().equals("")) {
                        hash.put("UserProfileUri", firebaseUser.getPhotoUrl().toString());
                    } else {
                        hash.put("UserProfileUri", "");
                    }
                    if (fromAdmin) {
                        String password=passwordForUserType.getText().toString();
                        hash.put("UserPassword",password);
                        if (!password.isEmpty()&&!password.equals(" ")){
                        signInWithEmailAndPassword(emailf,password);

                        }
                    } else {
                        WriteToFirebase writeToFirebase = new WriteToFirebase(getApplicationContext(), CompleteLogin.this);
                        writeToFirebase.addNewUserinfo(hash, bitmap, UserCompleteInfo);
                    }
                }
          }
        });

        // data pickicking
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override

            public void onClick(View v) {
                //Calendar calendar = Calendar.getInstance();//defining a calendar
                int year = 1990;//the year
                int month = 1; //calendar.get(Calendar.MONTH);//the month
                int day = 1; //calendar.get(Calendar.DAY_OF_MONTH);//the day
                //the date picker
                DatePickerDialog dateBD = new DatePickerDialog(CompleteLogin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //month is set to 0 so we need to add 1
                        userDate = Integer.toString(day) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year);
                        dateofbirth.setText(userDate);

                    }
                }, year, month, day);
                dateBD.show();


            }
        });
         /*
    /// this method make sure to get the right type of the user even if it is admin or doctor
    // if checked the user type is admin if not the user type is doctor
     */
        checkTheUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkTheUserType.setTextOff("Doctor");
                checkTheUserType.setTextOn("Admin");
                if (checkTheUserType.isChecked()) {
                    userType = "Admin";
                    checkTheUserType.setText("Admin");
                } else {
                    userType = "Doctor";
                    checkTheUserType.setText("Doctor");
                }
            }
        });


    }

    // for signing in  and add the data to the realtime DB
    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hash.put("UserId", mAuth.getCurrentUser().getUid());

                            WriteToFirebase writeToFirebase = new WriteToFirebase(getApplicationContext(), CompleteLogin.this);
                            writeToFirebase.addNewUserinfo(hash, bitmap, UserCompleteInfo);
                            FancyToast.makeText(getApplicationContext(), "Profile Created Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            // in case of connection is available
                            if (new InternetStatus(getApplicationContext()).checkNetworkStatus()) {
                                FancyToast.makeText(getApplicationContext(), "Wrong Email Or Pass"
                                        , FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                            // in case of connection to internet is lost
                            else {
                                FancyToast.makeText(getApplicationContext(), "Please Check Internet Connection"
                                        , FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                            }


                        }
                        // Disable the progressbar
                        disableControllers();
                    }
                });

    }


    // Disable controllers For protecting from diplicated data
    private void disableControllers() {
        image_profile.setEnabled(false);
        submit.setEnabled(false);
        username.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);
        address.setEnabled(false);
        dateofbirth.setEnabled(false);
        date.setEnabled(false);
    }


}