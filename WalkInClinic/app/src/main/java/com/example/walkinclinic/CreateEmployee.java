package com.example.walkinclinic;

import android.database.Cursor;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class CreateEmployee extends CreatePerson {
    EditText clinic;
    int x;
    String y;
    String checked;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = 0;
        y = "";
        setContentView(R.layout.activity_create_employee);
        clinic = findViewById(R.id.clinic);
        databaseHelper = new DatabaseHelper(this);
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onCreatePerson(View view) {
        x = checkClinic();
        if (x == 3){
            toastMessage("The clinic you entered has not been registered yet. Press Create Account again if you wish to create a new clinic profile.");
        } else {
            super.onCreatePerson(view);
        }
    }

    @Override
    boolean checkAllFields(){
        if(checkEmpty(firstName)){
            return true;
        }
        else if(checkEmpty(lastName)){
            return true;
        }
        else if (checkEmpty(Email)){
            return true;
        }
        else if (checkEmpty(Phone)){
            return true;
        }
        else if (checkEmpty(userName)){
            return true;
        }
        else if (checkEmpty(userPassword)){
            return true;
        }
        else if (checkEmpty(clinic)){
            return true;
        }
        else{
            return false;
        }
    }

    int checkClinic(){
        String work = clinic.getText().toString().trim();
        Cursor data = databaseHelper.getClinicData();
        if (work.equals(y)){
            x = 2;
        } else {
            x = 3;
            y = work;
        }
        while (data.moveToNext()){
            if (data.getString(1).equalsIgnoreCase(work)){
                x = 1;
            }
        }
        return x;
    }

    @Override
    void openPostLoggin() {
        addEmployee();
        //creating the string
        String postLogginString = ("Welcome Employee " + firstName.getText().toString() + "! You are logged-in.");
        String user = userName.getText().toString().trim();
        //opening the PostLoggin class and sending the message with it
        toastMessage(user);
        if (x == 2){
            Intent i = new Intent(this, ClinicProfile.class);
            i.putExtra("clinicName", clinic.getText().toString().trim());
            i.putExtra("username", user);
            startActivity(i);
        } else {
            Intent i = new Intent(this, PostLoggin.class);
            i.putExtra("message", postLogginString);
            i.putExtra("username", user);
            startActivity(i);
        }
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

    private void addEmployee() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String pos = clinic.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String user = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        boolean[] checkedItems = new boolean[getResources().getStringArray(R.array.insurance_types).length];
        checked = "";
        for (Boolean b: checkedItems){
                checked = checked + b + ", ";
        }
        boolean[] payments = new boolean[getResources().getStringArray(R.array.payment_types).length];
        String checkedPayments = "";
        for (Boolean b: payments){
            checkedPayments = checkedPayments + b + ", ";
        }
        databaseHelper.addDataA(fName, lName, pos, email, phone, user, password);
        if (x == 2) {
            String times = "00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00, 00:00";
            databaseHelper.addNewClinic(pos, checked, checkedPayments, times);
        }
    }
}
