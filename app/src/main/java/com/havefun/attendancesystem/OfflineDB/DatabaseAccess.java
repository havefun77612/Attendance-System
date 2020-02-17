package com.havefun.attendancesystem.OfflineDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
            instance.open();
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all Recordes from the database.
     *
     * @return a List of Records
     */
    /////////////////////////////////////////////  Feature Functions ////////////////////////////////////
    public List<String> getDof3aStudentsByStudentID(int id) {
        List<String> list = new ArrayList<>();
        String str = String.valueOf(id);
        char[] charArray = str.toCharArray();
        //System.out.println(charArray[1]);
        Cursor cursor = database.rawQuery("select * from student where StudentEductionalNumber like '"+charArray[0]+charArray[1]+"%' ;", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            System.out.print(cursor.getString(0)+" ");
            System.out.println(cursor.getString(1));
//            System.out.println(cursor.getString(2));
//            System.out.println(cursor.getString(3));
//            System.out.println(cursor.getString(4));
//            System.out.println(cursor.getString(5));
//            System.out.println(cursor.getString(6));
//            System.out.println(cursor.getString(7));
//            System.out.println(cursor.getString(8));
//            System.out.println(cursor.getString(9));
//            System.out.println(cursor.getString(10));
//            System.out.println(cursor.getString(11));
//            System.out.println(cursor.getString(12));
//            System.out.println(cursor.getString(13));
//            System.out.println(cursor.getString(14));
//            System.out.println(cursor.getString(15));
//            System.out.println(cursor.getString(16));
//            System.out.println(cursor.getString(17));
//            System.out.println(cursor.getString(18));
//            System.out.println(cursor.getString(19));
//            System.out.println(cursor.getString(20));
//            System.out.println(cursor.getString(21));
//            System.out.println(cursor.getString(22));
//            System.out.println(cursor.getString(23));
//            System.out.println(cursor.getString(24));

            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public ArrayList<String> getStudentCoursesByStudentID(int id){

        // Open the database for reading
        SQLiteDatabase db = openHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        // UserInfo pr;
        ArrayList<String> prArr=new ArrayList<>();
        try
        {
            String selectQuery = "SELECT *\n" +
                    "FROM Course\n" +
                    "WHERE ID in(SELECT CourseID\n" +
                    "FROM Course_semester\n" +
                    "WHERE ID IN (select Course_semesterID from Registration Where StudentID = " +
                    "(select ID from student Where StudentEductionalNumber = '"+id+"'))); ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    prArr.add(cursor.getString(3));



                    System.out.print(cursor.getString(0)+" ");
                    System.out.println(cursor.getString(3)+" ");

                }
                System.out.print(prArr.size()+" ");


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
    public ArrayList<String> getCoursesAvailableFCI(){

        // Open the database for reading
        SQLiteDatabase db = openHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        // UserInfo pr;
        ArrayList<String> prArr=new ArrayList<>();
        try
        {
            String selectQuery = "select * from Course " +
                    "where Code LIKE 'CS%' OR Code LIKE 'IT%' OR Code LIKE 'SE%' OR Code LIKE 'IS%' OR Code LIKE 'MATH%' OR Code LIKE 'M%' ; ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    prArr.add(cursor.getString(2));



                       System.out.println(cursor.getString(2)+" ");

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
    public ArrayList<String> getAllCoursesAvailable(){

        // Open the database for reading
        SQLiteDatabase db = openHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        // UserInfo pr;
        ArrayList<String> prArr=new ArrayList<>();
        try
        {
            String selectQuery = "select * from Course; ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    prArr.add(cursor.getString(2));



                       System.out.println(cursor.getString(2)+" ");

                }

                System.out.println(prArr.size());

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
    public ArrayList<String> getStudentInfoByStudentID(int id){

        // Open the database for reading
        SQLiteDatabase db = openHelper.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        // UserInfo pr;
        ArrayList<String> prArr=new ArrayList<>();
        try
        {
            String selectQuery = "select * From student where StudentEductionalNumber = "+id+"; ";
            //String countQuery="SELECT COUNT(*) FROM "+ dbHelper.TABLE_NAME2 +" ;";
            //Log.i("TheCountOfSelectedRows", countQuery+" ");
            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.getCount() >0)
            {
                for (int i =0;i<cursor.getCount();i++){
                    cursor.moveToNext();

                    prArr.add(cursor.getString(0));
                    prArr.add(cursor.getString(1));
                    prArr.add(cursor.getString(2));
                    prArr.add(cursor.getString(3));
                    prArr.add(cursor.getString(4));
                    prArr.add(cursor.getString(5));
                    prArr.add(cursor.getString(6));
                    prArr.add(cursor.getString(7));
                    prArr.add(cursor.getString(8));
                    prArr.add(cursor.getString(9));
                    prArr.add(cursor.getString(10));
                    prArr.add(cursor.getString(11));
                    prArr.add(cursor.getString(12));
                    prArr.add(cursor.getString(13));
                    prArr.add(cursor.getString(14));
                    prArr.add(cursor.getString(15));
                    prArr.add(cursor.getString(16));
                    prArr.add(cursor.getString(17));
                    prArr.add(cursor.getString(18));
                    prArr.add(cursor.getString(19));
                    prArr.add(cursor.getString(20));
                    prArr.add(cursor.getString(21));
                    prArr.add(cursor.getString(22));
                    prArr.add(cursor.getString(23));
                    prArr.add(cursor.getString(24));
                    System.out.println(cursor.getString(0));
                    System.out.println(cursor.getString(1));
                    System.out.println(cursor.getString(2));
                    System.out.println(cursor.getString(3));
                    System.out.println(cursor.getString(4));
                    System.out.println(cursor.getString(5));
                    System.out.println(cursor.getString(6));
                    System.out.println(cursor.getString(7));
                    System.out.println(cursor.getString(8));
                    System.out.println(cursor.getString(9));
                    System.out.println(cursor.getString(10));
                    System.out.println(cursor.getString(11));
                    System.out.println(cursor.getString(12));
                    System.out.println(cursor.getString(13));
                    System.out.println(cursor.getString(14));
                    System.out.println(cursor.getString(15));
                    System.out.println(cursor.getString(16));
                    System.out.println(cursor.getString(17));
                    System.out.println(cursor.getString(18));
                    System.out.println(cursor.getString(19));
                    System.out.println(cursor.getString(20));
                    System.out.println(cursor.getString(21));
                    System.out.println(cursor.getString(22));
                    System.out.println(cursor.getString(23));
                    System.out.println(cursor.getString(24));



                    //   System.out.print(prArr.get(i)+" ");

                }

                System.out.println(prArr.size());

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
}