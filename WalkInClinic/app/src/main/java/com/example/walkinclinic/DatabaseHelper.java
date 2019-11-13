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
    private static final String TABLE_CLINIC_NAME = "clinic_table";
    private static final String TABLE_CLINIC_SERVICES = "service_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "fname";
    private static final String COL3 = "lname";
    private static final String COL4 = "address";
    private static final String COL5 = "age";
    private static final String EMNUM = "address";
    private static final String COL6 = "email";
    private static final String COL7 = "phone";
    private static final String COL8 = "username";
    private static final String COL9 = "password";
    private static final String CLINIC = "clinic";
    private static final String SERVICES = "services";
    private static final String TIME = "TIME";

    private String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT)";
    private String createTableA = "CREATE TABLE " + TABLE_EMPLOYEE + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + EMNUM + " TEXT, " + CLINIC + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT, " + TIME + " TEXT)";
    private String createTableB = "CREATE TABLE " + TABLE_CLINIC_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLINIC + " TEXT)";
    private String createTableC = "CREATE TABLE " + TABLE_CLINIC_SERVICES + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SERVICES + " TEXT)";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createTable);
        db.execSQL(createTableA);
        db.execSQL(createTableB);
        db.execSQL(createTableC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_EMPLOYEE);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_CLINIC_NAME);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_CLINIC_SERVICES);
        onCreate(db);
    }

    public boolean addData(String fname, String lname, String address, String age, String email, String phone, String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, fname);
        contentValues.put(COL3, lname);
        contentValues.put(COL4, address);
        contentValues.put(COL5, age);
        contentValues.put(COL6, email);
        contentValues.put(COL7, phone);
        contentValues.put(COL8, user);
        contentValues.put(COL9, password);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataA(String fname, String lname, String emNum, String clinic, String email, String phone, String user, String password, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, fname);
        contentValues.put(COL3, lname);
        contentValues.put(EMNUM, emNum);
        contentValues.put(CLINIC, clinic);
        contentValues.put(COL6, email);
        contentValues.put(COL7, phone);
        contentValues.put(COL8, user);
        contentValues.put(COL9, password);
        contentValues.put(TIME, time);

        long result = db.insert(TABLE_EMPLOYEE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addNewClinic(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLINIC, name);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(SERVICES, "");

        long result = db.insert(TABLE_CLINIC_NAME, null, contentValues);
        long result2 = db.insert(TABLE_CLINIC_SERVICES, null, contentValues2);

        if (result == -1 && result2 == -1){
            return false;
        } else {
            return true;
        }
    }

    public void updateService(String serviceList, int id, String oldList){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CLINIC_SERVICES + " SET " + SERVICES + " = \"" + serviceList +
                "\" WHERE " + COL1 + " = \"" + id + "\" AND " + SERVICES + " = \"" + oldList + "\"";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting services to " + serviceList);
        db.execSQL(query);
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

    public Cursor getClinicData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CLINIC_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getServiceData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CLINIC_SERVICES;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPatient(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = \"" + id + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getEmployee(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL1 + " = \"" + id + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getClinicID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+TABLE_CLINIC_NAME+" WHERE " + CLINIC + " = \"" + name + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = \"" + id + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ id + " from database.");
        db.execSQL(query);
    }

    public void deleteNameA(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_EMPLOYEE + " WHERE " + COL1 + " = \"" + id + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ id + " from database.");
        db.execSQL(query);
    }
}
