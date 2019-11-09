package com.example.walkinclinic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.DatabaseErrorHandler;
import android.util.Log;

import java.lang.String;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "people_table";
    private static final String TABLE_EMPLOYEE = "employee_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "person";
    private static final String COL1A = "ID";
    private static final String COL2A = "employee";

    private String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
    private String createTableA = "CREATE TABLE " + TABLE_EMPLOYEE + " (" + COL1A + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2A + " TEXT)";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createTable);
        db.execSQL(createTableA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    public boolean addData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataA(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2A, name);

        long result = db.insert(TABLE_EMPLOYEE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataA(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+TABLE_NAME+" WHERE " + COL2 + " = \"" + name + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemIDA(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1A + " FROM "+TABLE_EMPLOYEE+" WHERE " + COL2A + " = \"" + name + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = \"" + id + "\"" + " AND " + COL2 + " = \"" + name + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ name + " from database.");
        db.execSQL(query);
    }

    public void deleteNameA(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_EMPLOYEE + " WHERE " + COL1A + " = \"" + id + "\"" + " AND " + COL2A + " = \"" + name + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ name + " from database.");
        db.execSQL(query);
    }
}
