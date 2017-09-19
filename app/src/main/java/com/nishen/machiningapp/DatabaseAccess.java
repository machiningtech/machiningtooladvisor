package com.nishen.machiningapp;

/**
 * Created by Nishen on 2017/09/18.
 */

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
     * Private constructor to avoid object creation from outside classes.
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
     * @return the instance of DatabaseAccess
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
     * Read all xxxxx from the database.
     *
     * @return a List of quotes
     */
    public List<String> getMaterials() {
        List<String> material_list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Description FROM Material", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            material_list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return material_list;
    }



}
