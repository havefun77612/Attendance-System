package com.havefun.attendancesystem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from student where StudentEductionalNumber like '16%' ;", null);
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
}