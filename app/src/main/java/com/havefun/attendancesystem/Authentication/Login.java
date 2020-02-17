package com.havefun.attendancesystem.Authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.havefun.attendancesystem.MainPage;
import com.havefun.attendancesystem.OfflineDB.DBManager;
import com.havefun.attendancesystem.R;
import com.havefun.attendancesystem.SplashScreen;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText password, email;
    TextView gotosginupbtn, login_word;
    Button loginbtn;
    String ErrorMessage = "";
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Animation Animate1, Animate2,Animate3;
    ImageView register_image, login_image;
     CardView card_login;
    //CardView card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);
        initializeVars();
        addVarsListner();
        addinganimation();
        addinganimationbyclicking();
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
                signInWithEmailAndPassword(Email, Password);
            } else {
                ErrorMessage = "Password can't be empty";
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
    ::::   Login User Stage ( Fetching The Account Data) Stage
     */
    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            DBManager offlineDB = new DBManager(getApplicationContext());
                            offlineDB.deleteAllRecordProf();
                            user = mAuth.getCurrentUser();
                            FancyToast.makeText(getApplicationContext(), "Login Success",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            // in case of connection is available
                            if (isonline()) {
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
                        disableProgressBar();
                    }
                });

    }


    /*
     ** Validation Methods
     */
    private boolean isValidPassword(String Password) {
        if (Password.isEmpty() || Password.contains(" ")) return false;
        else return true;
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
        loginbtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        email.setEnabled(true);
        password.setEnabled(true);
    }

    /*
     ** initialization Methods
     */

    private void addVarsListner() {
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbtn.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                GetData();
            }
        });
        gotosginupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initializeVars() {
        gotosginupbtn = (TextView) findViewById(R.id.gotosginupbtn);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        loginbtn = findViewById(R.id.loginbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        login_image = (ImageView) findViewById(R.id.login_image);
         card_login = (CardView)findViewById( R.id.card_login );
    }

    private void addinganimation() {
        Animate1 = AnimationUtils.loadAnimation( Login.this,R.anim.zoomin );
    //   Animate2 = AnimationUtils.loadAnimation( Login.this,R.anim.bounce );
        login_image.startAnimation( Animate1 );
        card_login.startAnimation( Animate1 );
        //login_word.startAnimation( Animate1 );
    }

    private void addinganimationbyclicking(){
        Animate2 = AnimationUtils.loadAnimation( Login.this,R.anim.bounce );
        Animate3 = AnimationUtils.loadAnimation( Login.this,R.anim.righttoleft );
        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
            loginbtn.startAnimation( Animate2 );
            email.startAnimation( Animate3 );
            password.startAnimation( Animate3 );
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
        super.onBackPressed();
    }
}
