package com.havefun.attendancesystem.OfflineDB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.havefun.attendancesystem.HelperClass.DoctorInfo;
import com.havefun.attendancesystem.HelperClass.UserInfo;
import com.havefun.attendancesystem.QR.UserData;
import com.havefun.attendancesystem.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.havefun.attendancesystem.OfflineDB.DatabaseHelper.TABLE_NAME2;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
        open();
        createProfileTable();
    }
/////////////////////////////////////  Before Using any Function In the DBManager Call Method Open() Frist //////////
    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
///////////////////////////////////////
    public void close() {
        dbHelper.close();
    }

/*
    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DatabaseHelper.DESC, desc);
        database.insert(TABLE_NAME, null, contentValue);
        System.out.println(" Inserted  ");
        //Toast.makeText(MainActivity,"0",Toast.LENGTH_LONG).show();
    }
    public void insert2(ArrayList<ContactModel> x) {
        ContentValues contentValue = new ContentValues();
        for (int i=0 ; i<x.size();i++){

            contentValue.put(DatabaseHelper.SUBJECT, x.get(i).getFristName());
            contentValue.put(DatabaseHelper.DESC, x.get(i).getLastName());
            database.insert(TABLE_NAME, null, contentValue);
        }
//        ContentValues contentValue = new ContentValues();
//        contentValue.put(DatabaseHelper.SUBJECT, name);
//        contentValue.put(DatabaseHelper.DESC, desc);

        System.out.println(" Inserted in "+ TABLE_NAME);
        //Toast.makeText(MainActivity,"0",Toast.LENGTH_LONG).show();
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
           // cursor.moveToFirst();
            ContactModel contact;
            for (int i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                contact=new ContactModel();
                contact.setId(cursor.getString(0));
                contact.setFristName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
               // contacts.add(contact);
                System.out.println(contact.getId());
                System.out.println(contact.getFristName());
                System.out.println(contact.getLastName());
            }

        }
        /*for (int i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                contact=new ContactModel();
                contact.setId(cursor.getString(0));
                contact.setFristName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contacts.add(contact);
            }
* /
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }
    public ArrayList<ContactModel> fetch2() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<ContactModel> contArr = new ArrayList<>();
        if (cursor != null) {
            // cursor.moveToFirst();
            ContactModel contact;
            for (int i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                contact=new ContactModel();
                contact.setId(cursor.getString(0));
                contact.setFristName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                /*
 contacts.add(contact);
                System.out.println(contact.getId());
                System.out.println(contact.getFristName());
                System.out.println(contact.getLastName());
* /
                contArr.add(i,contact);
            }

        }
        /*for (int i =0;i<cursor.getCount();i++){
                cursor.moveToNext();
                contact=new ContactModel();
                contact.setId(cursor.getString(0));
                contact.setFristName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contacts.add(contact);
            }
* /
        return  contArr;
    }

    public boolean noRecords(){
        ArrayList<ContactModel> contArr = new ArrayList<>();
        contArr=fetch2();
        if (contArr.size()==0)return true;
        else return false;
    }

   /* public void insertBitmap(Bitmap bm)  {

        // Convert the image into byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer=out.toByteArray();
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            values = new ContentValues();
            values.put("img", buffer);
            values.put("description", "Image description");
            // Insert Row
            long i = db.insert(TABLE_NAME, null, values);
            Log.i("Insert", i + "");
            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public Bitmap getBitmap(int id){
        Bitmap bitmap = null;
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT * FROM "+ TABLE_NAME+" WHERE id = " + id;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Convert blob data to byte array
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex("img"));
                    // Convert the byte array to Bitmap
                    bitmap= BitmapFactory.decodeByteArray(blob, 0, blob.length);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return bitmap;

    }* /
   public Bitmap getBitmap(int id){
       Bitmap bitmap = null;
       // Open the database for reading
       SQLiteDatabase db = dbHelper.getReadableDatabase();
       // Start the transaction.
       db.beginTransaction();

       try
       {
           String selectQuery = "SELECT * FROM "+ dbHelper.TABLE_NAME1+" WHERE id = " + id;
           Cursor cursor = db.rawQuery(selectQuery, null);
           if(cursor.getCount() >0)
           {
               while (cursor.moveToNext()) {
                   // Convert blob data to byte array
                   byte[] blob = cursor.getBlob(cursor.getColumnIndex("img"));
                   // Convert the byte array to Bitmap
                   bitmap= BitmapFactory.decodeByteArray(blob, 0, blob.length);

               }

           }
           db.setTransactionSuccessful();

       }
       catch (SQLiteException e)
       {
           e.printStackTrace();

       }
       finally
       {
           db.endTransaction();
           // End the transaction.
           db.close();
           // Close database
       }
       return bitmap;

   }
    public void insertBitmap(Bitmap bm)  {

        // Convert the image into byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer=out.toByteArray();
        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            values = new ContentValues();
            values.put("img", buffer);
            values.put("description", "Image description");
            // Insert Row
            long i = db.insert(dbHelper.TABLE_NAME1, null, values);
            Log.i("Insert", i + "");
            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public void createTableImg (){
        database.execSQL(dbHelper.CREATE_TABLE1);System.out.println(" Created Table Images ");
    }
*/

    ////////////////////////////////////////////////////////////////// Important Functions Table Prof //////////////////////////////////////////////////
    public void createProfileTable (){
        database.execSQL(dbHelper.CREATE_TABLE2);System.out.println(" Created Table Profile OR Exists Prof ");
    }
    public void insertProfileTable(ArrayList<UserInfo>  x, Bitmap bitmap)  {
       // createTableProf();


        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try
        {
            long k = 0;
            for (int i=0 ; i<x.size();i++) {
                // Convert the image into byte array

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                byte[] buffer=out.toByteArray();

                values = new ContentValues();

                values.put("img", buffer);
                values.put("UserId", x.get(i).getUserId());
                values.put("UserName", x.get(i).getUserName());
                values.put("UserEmail", x.get(i).getUserEmail());
                values.put("DateOfBirth", x.get(i).getDateOfBirth());
                values.put("UserAddress", x.get(i).getUserAddress());
                values.put("UserPhoneNumber", x.get(i).getUserPhoneNumber());

                // Insert Row
                 k = db.insert(dbHelper.TABLE_NAME2, null, values);
                 Log.i("InsertedTheIdNumber", k + "");
            }

            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public ArrayList<UserInfo> getProfileTable(){
        Bitmap bitmap = null;
        // Open the database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        UserInfo pr;
        ArrayList<UserInfo> prArr=new ArrayList<>();
        try
        {
            String selectQuery = "SELECT * FROM "+ dbHelper.TABLE_NAME2;
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();
                    // Convert blob data to byte array
                    byte[] blob = cursor.getBlob(cursor.getColumnIndex("img"));
                    // Convert the byte array to Bitmap
                    bitmap= BitmapFactory.decodeByteArray(blob, 0, blob.length);
                    pr=new UserInfo();
                    pr.setUserId(cursor.getString(0));
                    pr.setUserName(cursor.getString(1));
                    pr.setUserPhoneNumber(cursor.getString(2));
                    pr.setUserEmail(cursor.getString(3));
                    pr.setUserAddress(cursor.getString(4));
                    pr.setDateOfBirth(cursor.getString(5));
                    pr.setBitmap(bitmap);
                    prArr.add(i,pr);
                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return  prArr;

    }
    public boolean isEmptyTableProf(){
        ArrayList<UserInfo> prArr = new ArrayList<>();
        prArr=getProfileTable();
        if (prArr.size()==0)return true;
        else return false;
    }
    public void deleteAllRecordProf(){
        //database.execSQL("delete from "+ dbHelper.TABLE_NAME2);
        //database.delete(dbHelper.TABLE_NAME2, null, null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       // db.execSQL("DELETE FROM "+dbHelper.TABLE_NAME2);
        long k =db.delete(dbHelper.TABLE_NAME2, "1", null);//delete all rows in a table
        Log.i("NumberOfElementDeleted", k + "");
        db.close();

    }
    public void deleteElementByIdProf(long _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long k =db.delete(TABLE_NAME2, DatabaseHelper._ID + "=" + _id, null);
        Log.i("NumberOfElementDeleted", k + "");
        db.close();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////// Table Scan Functions   //////////////////////////////////////////////////////////////////
    public void createScanTable (){

        database.execSQL(dbHelper.CREATE_TABLE3);
        // System.out.println(" Created Table Profile OR Exists Scan");
    }
    public void insertScanٍTable(ArrayList<UserData>  x)  {
        // createTableProf();
        createScanTable();

        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            long k = 0;
            for (int i=0 ; i<x.size();i++) {
                // Convert the image into byte array


                values = new ContentValues();


                values.put("UserID", x.get(i).getID());
                values.put("UserName", x.get(i).getName());
                values.put("UserEmail", x.get(i).getEmail());
                values.put("UserDate", x.get(i).getDate());
                values.put("UserAddress", x.get(i).getAddress());
                values.put("UserPhone", x.get(i).getPhone());

                // Insert Row
                k = db.insert("Scan", null, values);
                StyleableToast.makeText( context, "data is inserted succefully ", R.style.insert ).show();

                Log.i("InsertedTheIdNumber", k + "");
            }

            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public ArrayList<UserData> getScanTable(){

        // Open the database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        UserData pr;
        ArrayList<UserData> prArr=new ArrayList<UserData>();
        try
        {
            String selectQuery = "SELECT * FROM Scan ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    pr=new UserData();
                    pr.setID(cursor.getString(2));
                    pr.setName(cursor.getString(1));
                    pr.setPhone(cursor.getString(4));
                    pr.setEmail(cursor.getString(3));
                    pr.setAddress(cursor.getString(5));
                    pr.setDate(cursor.getString(6));

                    System.out.println(cursor.getString(2));
                    System.out.println(cursor.getString(1));
                    System.out.println(cursor.getString(4));
                    System.out.println(cursor.getString(3));
                    System.out.println(cursor.getString(5));
                    System.out.println(cursor.getString(6));

                    prArr.add(i,pr);
                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        StyleableToast.makeText( context, "data is retrieved succefully ", R.style.retrieve ).show();

        return  prArr;

    }

    public boolean isEmptyTableScan() {
        ArrayList<UserData> prArr = new ArrayList<UserData>();
        prArr = getScanTable();
        if (prArr.size() == 0) {
            StyleableToast.makeText( context, "table is empty ", R.style.check_data ).show();
            return true;
        }
        else {
            StyleableToast.makeText( context, "table is not empty ", R.style.check_data ).show();
            return false;
        }
    }
    public void deleteAllRecordScan(){
        //database.execSQL("delete from "+ dbHelper.TABLE_NAME2);
        //database.delete(dbHelper.TABLE_NAME2, null, null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // db.execSQL("DELETE FROM "+dbHelper.TABLE_NAME2);
        long k =db.delete("Scan", "1", null);//delete all rows in a table
        StyleableToast.makeText( context, "data is deleted succefully ", R.style.delete ).show();

        Log.i("NumberOfElementDeleted", k + "");
        db.close();
    }
    public void deleteElementByIdScan(long _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long k =db.delete("Scan", "ID = " + _id, null);
        Log.i("NumberOfElementDeleted", k + "");
        db.close();
    }
    //////////////////////////////////////////////////////////// Table Doctor_Courses Functions   /////////////////////////////////////////
    public void createDoctor_CoursesTable (){

        database.execSQL(dbHelper.CREATE_TABLE4);System.out.println(" Created Table Profile OR Exists Doctor_Courses ");
    }
    public void insertDoctor_CoursesٍTable(ArrayList<DoctorInfo>  x)  {
        // createTableProf();
        createDoctor_CoursesTable();

        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            long k = 0;
            for (int i=0 ; i<x.size();i++) {
                // Convert the image into byte array


                values = new ContentValues();


                values.put("DoctorName", x.get(i).getDoctorName());
                values.put("CourseName", x.get(i).getCourseName());
                values.put("DoctorId", x.get(i).getDoctorId());

                // Insert Row
                k = db.insert("Doctor_Courses", null, values);
                Log.i("InsertedTheIdNumber", k + "");
            }

            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public ArrayList<DoctorInfo> getDoctor_CoursesTable(){

        // Open the database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        DoctorInfo pr;
        ArrayList<DoctorInfo> prArr=new ArrayList<DoctorInfo>();
        try
        {
            String selectQuery = "SELECT * FROM Doctor_Courses ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    pr=new DoctorInfo();
                    pr.setDoctorId(cursor.getString(3));
                    pr.setDoctorName(cursor.getString(1));
                    pr.setCourseName(cursor.getString(2));


                    System.out.println(cursor.getString(3));
                    System.out.println(cursor.getString(1));
                    System.out.println(cursor.getString(2));
                    System.out.println(cursor.getString(0));

                    prArr.add(i,pr);
                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return  prArr;

    }
    public boolean isEmptyTableDoctor_Courses(){
        ArrayList<DoctorInfo> prArr = new ArrayList<DoctorInfo>();
        prArr=getDoctor_CoursesTable();
        if (prArr.size()==0)return true;
        else return false;
    }
    public void deleteAllRecordDoctor_Courses(){
        //database.execSQL("delete from "+ dbHelper.TABLE_NAME2);
        //database.delete(dbHelper.TABLE_NAME2, null, null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // db.execSQL("DELETE FROM "+dbHelper.TABLE_NAME2);
        long k =db.delete("Doctor_Courses", "1", null);//delete all rows in a table
        Log.i("NumberOfElementDeleted", k + "");
        db.close();

    }
    public void updateDoctor_CoursesٍTable(ArrayList<DoctorInfo>  x ,String NewCourseName )  {


        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            long k = 0;
            for (int i=0 ; i<x.size();i++) {
                // Convert the image into byte array


                values = new ContentValues();


                values.put("DoctorName", x.get(i).getDoctorName());
                values.put("CourseName", NewCourseName);


                // Insert Row
               k= db.update("Doctor_Courses",values, "DoctorName" + " =? ", new String[]{ x.get(i).getDoctorName()} );

                Log.i("UpdatedTheCourseName", k + "");
            }

            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    //////////////////////////////////////////////////////////   Table DataExist Functions   ///////////////////////////////
    public void createDataExistTable (){

        database.execSQL(dbHelper.CREATE_TABLE5);System.out.println(" Created Table Profile OR Exists 'DataExist' ");
    }
    public void insertDataExistٍTable(String UserName,String UserID, int isUploaded)  {
        // createTableProf();
        createDataExistTable();

        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues values;

        try
        {
            long k = 0;



                values = new ContentValues();


                values.put("UserName", UserName);
                values.put("UserId", UserID);
                values.put("Uploaded", isUploaded);

                // Insert Row
                k = db.insert("DataExist", null, values);
                Log.i("InsertedTheIdNumber", k + "");


            // Insert into database successfully.
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
    }
    public ArrayList<String> getDataExistTable(){

        // Open the database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        ArrayList<String> prArr=new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM DataExist ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {cursor.moveToNext();



                    prArr.add(0,cursor.getString(2));
                    prArr.add(1,cursor.getString(1));
                    prArr.add(2,cursor.getString(3));


                    System.out.println(cursor.getString(2));
                    System.out.println(cursor.getString(1));
                    System.out.println(cursor.getString(3));
                    System.out.println(cursor.getString(0));




            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }
        return  prArr;

    }
    public void deleteAllRecordDataExist(){
        //database.execSQL("delete from "+ dbHelper.TABLE_NAME2);
        //database.delete(dbHelper.TABLE_NAME2, null, null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // db.execSQL("DELETE FROM "+dbHelper.TABLE_NAME2);
        long k =db.delete("DataExist", "1", null);//delete all rows in a table
        Log.i("NumberOfElementDeleted", k + "");
        db.close();

    }
    public boolean isEmptyTableDataExist(){
        ArrayList<String> prArr = new ArrayList<String>();
        prArr=getDataExistTable();
        if (prArr.size()==0)return true;
        else return false;
    }
//    public void DropTableDataExist(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS DataExist" );
//        System.out.println("DataExist Table Deleted");
//    }
    public void updateDataExistٍTable(String UserID, int isUploaded)  {


    // Open the database for writing
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    // Start the transaction.
    db.beginTransaction();
    ContentValues values;

    try
    {
        long k = 0;



        values = new ContentValues();


        values.put("Uploaded", isUploaded);



        // Insert Row
        k= db.update("DataExist",values, "UserId" + " =? ", new String[]{ UserID} );

        Log.i("UpdatedTheCourseName", k + "");


        db.setTransactionSuccessful();

    }
    catch (SQLiteException e)
    {
        e.printStackTrace();

    }
    finally
    {
        db.endTransaction();
        // End the transaction.
        db.close();
        // Close database
    }
}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}




