package com.havefun.attendancesystem.OfflineDB;

/**
 * Created by anupamchugh on 19/10/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    //public static final String TABLE_NAME = "COUNTRIES";
    //public static final String TABLE_NAME1 = "imgs";
    public static final String TABLE_NAME2 = "Prof";
    // Table columns
    public static final String _ID = "_id";
    //public static final String SUBJECT = "subject";
    //public static final String DESC = "description";

    // Database Information
    static final String DB_NAME = "AttendanceSYS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
//    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + _ID
//            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DESC + " TEXT);";
//
//    public static final String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME1+
//            "(id INTEGER PRIMARY KEY AUTOINCREMENT, img BLOB NOT NULL, description TEXT )";

    public static final String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME2+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,UserName TEXT,UserPhoneNumber TEXT,UserEmail TEXT,UserAddress TEXT, " +
            "  DateOfBirth TEXT,UserId Text ,  img BLOB NOT NULL )";
    public static final String CREATE_TABLE3 = "CREATE TABLE IF NOT EXISTS Scan (\n" +
            "    ID          INTEGER        PRIMARY KEY AUTOINCREMENT\n" +
            "                                            NOT NULL,\n" +
            "    UserName    Text ,\n" +
            "    UserID      Text ,\n" +
            "    UserEmail   Text ,\n" +
            "    UserPhone   Text ,\n" +
            "    UserAddress Text ,\n" +
            "    UserDate    Text);";
    public static final String CREATE_TABLE4 ="CREATE TABLE IF NOT EXISTS Doctor_Courses (\n" +
            " ID                       INTEGER        PRIMARY KEY AUTOINCREMENT\n" +
            "                                            NOT NULL,\n" +
            "  DoctorName              Text ,\n" +
            "  CourseName              Text ," +
            "DoctorId INTEGER );";
    public static final String CREATE_TABLE5 ="CREATE TABLE IF NOT EXISTS DataExist (\n" +
            " ID                       INTEGER        PRIMARY KEY AUTOINCREMENT\n" +
            "                                            NOT NULL,\n" +
            "  UserName              Text ,\n" +
            "  UserId              Text,\n" +
            "  Uploaded                INTEGER Not Null );";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        db.execSQL(CREATE_TABLE5);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }





}

