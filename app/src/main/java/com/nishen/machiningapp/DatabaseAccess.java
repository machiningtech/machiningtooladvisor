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

    //String method. Suitable for single textview.
    public List<String> getMaterials() {
        List<String> material_list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Description FROM Material GROUP BY ID", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            material_list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return material_list;
    }

    public Cursor getMaterialsCursor() {
        Cursor materialsCursor = database.rawQuery("SELECT SMG, Description FROM Material", null);
        return materialsCursor;

    }

    public Cursor FilterToolsCursor(String profile, String material) {
        Cursor materialCursor = database.rawQuery("SELECT SMG FROM Material WHERE ID =" + material, null);
        materialCursor.moveToFirst();
        String SMG = materialCursor.getString(0);
        Cursor cursor = database.rawQuery("SELECT Tool.Name, Dc, ap, zn, Part_No, Tool_Shape, dmm, l2, re1, rake, coolant, \"Ap/Dc\", \"Ae/Dc\", \"6\", \"8\", \"10\", \"12\", Vc FROM Tool, Cutdata WHERE Profile LIKE '%" + profile + "%' AND Tool.Material LIKE '%" + SMG + "%' AND Cutdata.Material LIKE '%" + SMG + "%' AND Cutdata.Name = Tool.Name AND Cutdata.Operation LIKE '%" + profile + "%'", null);
        return cursor;
    };


 /**   public List<String> FilterTools(String profile) {
        List<String> usable_tools = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT "+ "Part_No" +" FROM Tool WHERE " + "Profile" + " LIKE '%" + profile + "%'", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usable_tools.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return usable_tools;
    }**/

    public List<String> unique_corner_radius() {
        List<String> corner_radius_list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT re1 FROM Tool ORDER BY re1 DESC", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            corner_radius_list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return corner_radius_list;
    }

    public List<String> getmachines() {
        List<String> machine_list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Name FROM Machine ORDER BY ID ASC", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            machine_list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return machine_list;
    }





}
