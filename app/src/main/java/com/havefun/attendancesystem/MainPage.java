package com.havefun.attendancesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.havefun.attendancesystem.Authentication.Login;
import com.havefun.attendancesystem.Chat.MainChat;
import com.havefun.attendancesystem.HelperClass.ReadExcelData;
import com.havefun.attendancesystem.OfflineDB.DBManager;
import com.havefun.attendancesystem.OfflineDB.DatabaseAccess;
import com.havefun.attendancesystem.Profile.CompleteLogin;
import com.havefun.attendancesystem.Profile.ProfileActivity;
import com.havefun.attendancesystem.QR.QrGen;
import com.havefun.attendancesystem.QR.Qrcour;
import com.havefun.attendancesystem.QR.ScanCourse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;


public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    LinearLayout loginActivity, profile_page, scaner, chatting, qrgeneration, qrCourse, addNewDoctorOrAdmin;
    CardView cardLogin,cardProfile,cardQrGeneration,cardChat,cardQrScan,cardQrCourseGeneration,cardAddNewUserType;
    GridLayout mainPageMainContainer;
    FirebaseUser user;
    DBManager offlineDB;
    TextView main_page;
    ImageView image_profile, image_login, image_service, image_qr, image_scan;
    Animation Animate1, Animate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initializeVars();
        prepareContentDependingOnTheUserType();
        addListners();
        addinganimation();
        ////////////////////////////////// Test Faculty Database /////////////////////////////////
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        //databaseAccess.open();
        List<String> quotes = databaseAccess.getAllCoursesAvailable();
        System.out.println(quotes.size());
        ///////////////////////////
        //////////////////////////////////Test Scan Table ////////////////////////////////////////////

        /*ArrayList<UserInfo> arrUs= new ArrayList<UserInfo>();
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId("1");
        userInfo.setUserName("Aloo");
        userInfo.setDateOfBirth("1.6.1998");
        userInfo.setUserAddress("sdugaugkcnnxc s");
        userInfo.setUserPhoneNumber("01124587564");
        userInfo.setUserEmail("Ali@rmail.com");
        arrUs.add(userInfo);
        DBManager d=new DBManager(getApplicationContext());
        d.insertScanٍTable(arrUs);
        d.getScanTable();

        System.out.println(d.isEmptyTableScan());
        d.deleteAllRecordScan();
        System.out.println(d.isEmptyTableScan());*/
        ///////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////Test Doctor_Courses Table ////////////////////////////////

       /* ArrayList<DoctorInfo> arrUs= new ArrayList<DoctorInfo>();
        DoctorInfo doctorInfo=new DoctorInfo();
        doctorInfo.setCourseName("DataCommunication");
        doctorInfo.setDoctorName("fayza");
        doctorInfo.setDoctorId("1");
        arrUs.add(doctorInfo);
        DBManager d=new DBManager(getApplicationContext());
        d.getDoctor_CoursesTable();
        d.updateDoctor_CoursesٍTable(arrUs);
        d.getDoctor_CoursesTable();

        System.out.println(d.isEmptyTableDoctor_Courses());
        d.deleteAllRecordDoctor_Courses();
        System.out.println(d.isEmptyTableDoctor_Courses());*/
        ///////////////////////////////////////////////////////Test Table DataExist ////////////////////
       /* DBManager d=new DBManager(getApplicationContext());
       // d.insertDataExistٍTable("Ali","161014",0);
       d.getDataExistTable();
        System.out.println(d.isEmptyTableDataExist());
        d.updateDataExistٍTable("161014",1);
        d.getDataExistTable();
        d.deleteAllRecordDataExist();
        d.isEmptyTableDataExist();*/


        //////////////////////////////////////////////////// End Of Test ////////////////

        testExcellFile();
    }
/*
Remember to enable view depending on the user type comment  this line SplashScreen.FirebaseUserType="Admin";
 */
    private void prepareContentDependingOnTheUserType() {
        // to Disable the user type uncomment for Debugging the below line ==>
       // SplashScreen.FirebaseUserType="Admin";
        if (SplashScreen.FirebaseUserType.equals("Student")) {
            mainPageMainContainer.removeView(cardLogin);
            mainPageMainContainer.removeView(cardQrScan);
            mainPageMainContainer.removeView(cardQrGeneration);
            mainPageMainContainer.removeView(cardQrCourseGeneration);
            mainPageMainContainer.removeView(cardAddNewUserType);
        } else if (SplashScreen.FirebaseUserType.equals("Doctor")) {
            mainPageMainContainer.removeView(cardQrGeneration);
            mainPageMainContainer.removeView(cardQrCourseGeneration);
            mainPageMainContainer.removeView(cardAddNewUserType);
        } else if (SplashScreen.FirebaseUserType.equals("Admin")) {
           //mainPageMainContainer.removeView(cardLogin);
            FancyToast.makeText(getApplicationContext(),"Hello Admin",
                    FancyToast.LENGTH_LONG,FancyToast.INFO,true).show();
        } else {
            Toast.makeText(this, "No UserType Exist", Toast.LENGTH_SHORT).show();
            System.out.println("No UserType Exist");
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.menulogout: {
                logOut();
                break;
            }
        }

        return true;
    }
    /*
     **** User info Handling
     */

    private void logOut() {
        offlineDB.deleteAllRecordProf();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    /*
     ** Initialization Stage
     */
    private void initializeVars() {
        loginActivity = (LinearLayout) findViewById(R.id.loginactivity);
        profile_page = (LinearLayout) findViewById(R.id.profile_page);
        scaner = (LinearLayout) findViewById(R.id.Scaner);
        chatting = (LinearLayout) findViewById(R.id.chattingCard);
        qrgeneration = (LinearLayout) findViewById(R.id.qrgeneration);
        addNewDoctorOrAdmin = findViewById(R.id.addNewDoctorOrAdmin);
        cardChat=findViewById(R.id.cardChat);
        cardLogin=findViewById(R.id.cardLogin);
        cardProfile=findViewById(R.id.cardProfile);
        cardAddNewUserType=findViewById(R.id.cardAddNewUserType);
        cardQrCourseGeneration=findViewById(R.id.cardCourseGeneration);
        cardQrGeneration=findViewById(R.id.cardQrGeneration);
        cardQrScan=findViewById(R.id.cardScan);
        qrCourse = findViewById(R.id.QrCourse);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainPageMainContainer = findViewById(R.id.mainPageMainContainer);
        user = FirebaseAuth.getInstance().getCurrentUser();
        offlineDB = new DBManager(getApplicationContext());
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.draw_dev);
        navigationView.setNavigationItemSelectedListener(this);
        image_profile = (ImageView) findViewById(R.id.image_profile);
        image_login = (ImageView) findViewById(R.id.image_login);
        image_service = (ImageView) findViewById(R.id.image_service);
        image_qr = (ImageView) findViewById(R.id.image_qr);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        main_page = (TextView) findViewById(R.id.main_page);
    }
     /*
     * inializing animation
     */
    private void addinganimation() {
        Animate1 = AnimationUtils.loadAnimation(MainPage.this, R.anim.zoomin);
       // Animate2 = AnimationUtils.loadAnimation(MainPage.this, R.anim.zoomout);
        image_scan.startAnimation(Animate1);
        image_qr.startAnimation(Animate1);
        image_login.startAnimation(Animate1);
        image_profile.startAnimation(Animate1);
        image_service.startAnimation(Animate1);
        //main_page.startAnimation(Animate2);
    }


    private void addListners() {
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
        });

        profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });
        scaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCourse.class));

            }
        });
        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainChat.class));

            }
        });
        qrgeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QrGen.class));
            }
        });
        qrCourse.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(getApplicationContext(), Qrcour.class));
                                        }
                                    }

        );
        addNewDoctorOrAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserTyprSelectionActivity();
            }
        });

    }

    // Testing the Excell Sheet Values
    private void testExcellFile() {
       // ReadExcelData readExcelData = new ReadExcelData(getApplicationContext());
        // uncomment the below link for testing ==>
      //  readExcelData.order();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openUserTyprSelectionActivity() {
        Intent forUserType = new Intent(getApplicationContext(), CompleteLogin.class);
        forUserType.putExtra("fromAdmin", true);
        startActivity(forUserType);
    }
}
