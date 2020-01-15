package com.havefun.attendancesystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.havefun.attendancesystem.Profile.CompleteLogin;
import com.havefun.attendancesystem.Profile.ProfileActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    TextView gotologinbtn;
    EditText password, email;
    String ErrorMessage = "";
    FrameLayout sginupbtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    SignInButton google;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String TAG = "SignUp: ";
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initializeVars();
        addVarsListner();
        facebookConnecton();
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
                returnContentControle();
            }
        } else {
            ErrorMessage = "Sorry! Invalid Email";
            email.setError(ErrorMessage);
            // disable the progressbar
            returnContentControle();
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
                            user = mAuth.getCurrentUser();
                            checkifUserCompeletLoginOrNot();

                        } else {
                            // If sign in fails, display a message to the user.
                            if (isonline()) {
                                FancyToast.makeText(getApplicationContext(), task.getException().getMessage(), FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                            } else {
                                FancyToast.makeText(getApplicationContext(), "Check Your Internet", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }

                        }
//Disable the Progressbar
                        returnContentControle();
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
        ///////// Google Sign in part
     */


    /*
        +++  Google Sign in Methods
 */
    private void googleSignIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    /*
====== To Listen For teh Result OF Google Sined in User
   */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        // Note the RequestCode Must Be The Same Asa the Google Code USed
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
                //   progressBar.setVisibility(View.INVISIBLE);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                FancyToast.makeText(getApplicationContext(), "Google sign in failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                // ...

                Log.w("mMessage", "Google sign in failed", e);
                //   progressBar.setVisibility(View.INVISIBLE);
            }


        }

    }


    /*
     ///// Authonticate With Google
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Toast.makeText(this, "firebaseAuthWithGoogle:" + acct.getId(), Toast.LENGTH_SHORT).show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FancyToast.makeText(getApplicationContext(), "signIn succefully"
                                    , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            user = mAuth.getCurrentUser();
                            checkifUserCompeletLoginOrNot();

                        } else {
                            // If sign in fails, display a message to the user.
                            FancyToast.makeText(getApplicationContext(), "signIn failed ", FancyToast.LENGTH_LONG, FancyToast.ERROR
                                    , true).show();

                        }

                    }
                });
    }



    /*
     *** Login with facebook
     */
// Initialize Facebook Login button

    void facebookConnecton() {

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        FancyToast.makeText(getApplicationContext(), "Facbook proccessing", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                        FancyToast.makeText(getApplicationContext(), "Facbook Login Canceled", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        FancyToast.makeText(getApplicationContext(), "Facbook ERROR" + error.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                             user = mAuth.getCurrentUser();
                            FancyToast.makeText(getApplicationContext(), "Facbook Signup succeded", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            checkifUserCompeletLoginOrNot();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            FancyToast.makeText(getApplicationContext(), "Facbook Signup Failed", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


                        }

                    }
                });
    }

    /*
    ::::::
     */
void checkifUserCompeletLoginOrNot(){
    Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("UserId").equalTo(user.getUid());
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }else {
            finish();
            startActivity(new Intent(getApplicationContext(), CompleteLogin.class));
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

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

                disableBtns();
                GetData();
                returnContentControle();
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableBtns();
                googleSignIn();
                returnContentControle();
            }
        });

    }

    private void initializeVars() {
        gotologinbtn = (TextView) findViewById(R.id.gotologinbtn);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        google = findViewById(R.id.google);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        sginupbtn = (FrameLayout) findViewById(R.id.sginupbtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);


    }

    void disableBtns() {
        progressBar.setVisibility(View.VISIBLE);
        google.setEnabled(false);
        sginupbtn.setEnabled(false);
        loginButton.setEnabled(false);

    }

    void returnContentControle() {
        progressBar.setVisibility(View.GONE);
        google.setEnabled(true);
        sginupbtn.setEnabled(true);
        loginButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
        super.onBackPressed();
    }
}
