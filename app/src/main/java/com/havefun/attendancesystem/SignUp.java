package com.havefun.attendancesystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView gotologinbtn;
    EditText password, email;
    String ErrorMessage = "";
    FrameLayout sginupbtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initializeVars();
        addVarsListner();
    }

    /*
     ** preparing data stage
     */
    private void GetData() {
        String Password, Email;
        Password = password.getText().toString();
        Email = email.getText().toString();
        ValidateData(Email, Password);
    }

    private void ValidateData(String Email, String Password) {
        if (isValidEmail(Email)) {
            if (isValidPassword(Password)) {
                createNewAccount(Email, Password);
            } else {
                ErrorMessage = "Week Password ! Your Pass should contain special character " +
                        "\nlower case , upper case ,numbers & No spaces";
                password.setError(ErrorMessage);
              // disable The progressbar
                disableProgressBar();
            }
        } else {
            ErrorMessage = "Sorry! Invalid Email";
            email.setError(ErrorMessage);
            // disable the progressbar
             disableProgressBar();
        }

        ErrorMessage = "";
    }

    /*
    ::::   Creating User Stage ( Uploading The Account Data) Stage
     */
    private void createNewAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FancyToast.makeText(getApplicationContext(), "Signed up Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            if (isonline()) {
                                FancyToast.makeText(getApplicationContext(), task.getException().getMessage(), FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                            } else {
                                FancyToast.makeText(getApplicationContext(), "Check Your Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }

                        }
//Disable the Progressbar
                        disableProgressBar();
                    }
                });
    }


    /*
     ** Validation Methods
     */
    private boolean isValidPassword(String Password) {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(Password);
        return matcher.matches();
    }

    private boolean isValidEmail(String Email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }

    /*
     **** Check The Device State OF internet Connection
     */
    private boolean isonline() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    /*
    :::: Disable The Progress View
     */

    private void disableProgressBar() {
        sginupbtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        email.setEnabled(true);
        password.setEnabled(true);
    }

    /*
     ** initialization Methods
     */

    private void addVarsListner() {
        gotologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        sginupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sginupbtn.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                GetData();
            }
        });
    }

    private void initializeVars() {
        gotologinbtn = (TextView) findViewById(R.id.gotologinbtn);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        sginupbtn = (FrameLayout) findViewById(R.id.sginupbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainPage.class));
        finish();
        super.onBackPressed();
    }
}
