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
    private static final String TABLE_CLINIC = "clinic_table";
    private static final String TABLE_SERVICES = "service_table";
    private static final String TABLE_APPOINTMENTS = "appointment_requests";
    private static final String COL1 = "ID";
    private static final String COL2 = "fname";
    private static final String COL3 = "lname";
    private static final String COL4 = "address";
    private static final String COL5 = "age";
    private static final String COL6 = "email";
    private static final String COL7 = "phone";
    private static final String COL8 = "username";
    private static final String COL9 = "password";
    private static final String CLINIC = "clinic";
    private static final String SERVICES = "services";
    private static final String SERVICESBOOL = "bool_services";
    private static final String SERVICESINT = "int_services";
    private static final String ICHECKED = "bool_insurance";
    private static final String ISELECTED = "int_insurance";
    private static final String PCHECKED = "bool_payment";
    private static final String PSELECTED = "int_payment";
    private static final String HOURS = "hours";
    private static final String LAT = "latitude";
    private static final String LONG = "longitude";
    private static final String RATING = "rating";
    private static final String WAITTIME = "waittime";
    private static final String PATIENT = "patient_username";
    private static final String DATE = "date_requested";

    private String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT)";
    private String createTableA = "CREATE TABLE " + TABLE_EMPLOYEE + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + CLINIC + " TEXT, " + COL6 + " TEXT, " + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT, " + HOURS + " TEXT)";
    private String createTableB = "CREATE TABLE " + TABLE_CLINIC + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CLINIC + " TEXT COLLATE NOCASE, " + ICHECKED + " TEXT, " + ISELECTED + " TEXT, " + PCHECKED + " TEXT, " + PSELECTED + " TEXT, " + SERVICESBOOL + " TEXT, " + SERVICESINT + " TEXT, " + HOURS + " TEXT, " + COL7 + " TEXT, " + LAT + " REAL, " + LONG + " REAL, " + RATING + " REAL, " + WAITTIME + " INT)";
    private String createTableC = "CREATE TABLE " + TABLE_SERVICES + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SERVICES + " TEXT)";
    private String createTableD = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" + CLINIC + " TEXT, " + PATIENT + " TEXT, " + DATE + " TEXT)";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(createTable);
        db.execSQL(createTableA);
        db.execSQL(createTableB);
        db.execSQL(createTableC);
        db.execSQL(createTableD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_EMPLOYEE);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_CLINIC);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_SERVICES);
        db.execSQL((String) "DROP IF TABLE EXISTS " + TABLE_APPOINTMENTS);
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

    public boolean addDataA(String fname, String lname, String clinic, String email, String phone, String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, fname);
        contentValues.put(COL3, lname);
        contentValues.put(CLINIC, clinic);
        contentValues.put(COL6, email);
        contentValues.put(COL7, phone);
        contentValues.put(COL8, user);
        contentValues.put(COL9, password);
        contentValues.put(HOURS, "");

        long result = db.insert(TABLE_EMPLOYEE, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addNewClinic(String name, String checkedBool, String paymentBool, String times){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLINIC, name);
        contentValues.put(ICHECKED, checkedBool);
        contentValues.put(ISELECTED, "");
        contentValues.put(PCHECKED, paymentBool);
        contentValues.put(PSELECTED, "");
        contentValues.put(SERVICESBOOL, "");
        contentValues.put(SERVICESINT, "");
        contentValues.put(HOURS, times);
        contentValues.put(COL7, "Phone");
        contentValues.put(LAT, 0.0);
        contentValues.put(LONG, 0.0);
        contentValues.put(RATING, -1);
        contentValues.put(WAITTIME, -1);
        long result = db.insert(TABLE_CLINIC, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addService(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICES, name);

        long result = db.insert(TABLE_SERVICES, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addAppointment(String clinic, String patient, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLINIC, clinic);
        contentValues.put(PATIENT, patient);
        contentValues.put(DATE, date);
        long result = db.insert(TABLE_APPOINTMENTS, null, contentValues);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public void updateService(String newService, int id, String oldService){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_SERVICES + " SET " + SERVICES + " = \"" + newService +
                "\" WHERE " + COL1 + " = \"" + id + "\" AND " + SERVICES + " = \"" + oldService + "\"";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting services to " + newService);
        db.execSQL(query);
    }

    public void updateClinic(String name, String bools, String ints, String pbools, String pints, String sbools, String sints, String hours, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ICHECKED, bools);
        contentValues.put(ISELECTED, ints);
        contentValues.put(PCHECKED, pbools);
        contentValues.put(PSELECTED, pints);
        contentValues.put(SERVICESBOOL, sbools);
        contentValues.put(SERVICESINT, sints);
        contentValues.put(HOURS, hours);
        contentValues.put(COL7, phone);
        db.update(TABLE_CLINIC, contentValues, CLINIC +"=?", new String[]{name});
    }

    public void updateLatitue(String name, Double lati){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CLINIC + " SET " + LAT + " = \"" + lati +
                "\" WHERE " + CLINIC + " = \"" + name + "\" COLLATE NOCASE ";
        db.execSQL(query);
    }

    public void updateLogitude(String name, Double longi){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CLINIC + " SET " + LONG + " = \"" + longi +
                "\" WHERE " + CLINIC + " = \"" + name + "\" COLLATE NOCASE ";
        db.execSQL(query);
    }

    public void updateWaitTime(String name, int time){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CLINIC + " SET " + WAITTIME + " = \"" + time +
                "\" WHERE " + CLINIC + " = \"" + name + "\" COLLATE NOCASE ";
        db.execSQL(query);
    }

    public void updateRating(String name, float rating){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CLINIC + " SET " + RATING + " = \"" + rating +
                "\" WHERE " + CLINIC + " = \"" + name + "\" COLLATE NOCASE ";
        db.execSQL(query);
    }

    public void updateEmployeeHours(String username, String hours){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOURS, hours);
        db.update(TABLE_EMPLOYEE, contentValues, COL8 +"=?", new String[]{username});
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
        String query = "SELECT * FROM " + TABLE_CLINIC;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getExistingClinic(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CLINIC + " WHERE " + CLINIC + " = \"" + name + "\" COLLATE NOCASE";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getServiceData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SERVICES;
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

    public Cursor getEmployee(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL8 + " = \"" + user + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPatient(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL8 + " = \"" + user + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getClinicID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+TABLE_CLINIC+" WHERE " + CLINIC + " = \"" + name + "\"";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getServiceID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+TABLE_SERVICES+" WHERE " + SERVICES + " = \"" + name + "\"";
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

    public void deleteClinic(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_CLINIC + " WHERE " + COL1 + " = \"" + id + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ id + " from database.");
        db.execSQL(query);
    }

    public void deleteService(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_SERVICES + " WHERE " + COL1 + " = \"" + id + "\"";
        Log.d(TAG, "deleteName: query: "+ query);
        Log.d(TAG, "deleteName: Deleting: "+ id + " from database.");
        db.execSQL(query);
    }
}
