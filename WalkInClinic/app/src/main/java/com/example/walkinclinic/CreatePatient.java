package com.example.walkinclinic;

import android.os.Bundle;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CreatePatient extends CreatePerson {
    EditText a;
    EditText streetAddress;
    private static final String TAG = "CreatePatient";

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        a = findViewById(R.id.Age);
        streetAddress = findViewById(R.id.StreetAddress);
        databaseHelper = new DatabaseHelper(this);
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onCreatePerson(View view){
        super.onCreatePerson(view);
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
        else if (checkEmpty(a)) {
            return true;
        }
        else if (checkEmpty(streetAddress)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    void openPostLoggin() {
        addPatient();
        //creating the string
        String postLogginString = ("Welcome Patient " + firstName.getText().toString() + "! You are logged-in.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

    private void addPatient() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String address = streetAddress.getText().toString().trim();
        String age = a.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String user = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String patient = fName + ", " + lName + ", " + address + ", " + age + ", " + email + ", " + phone + ", " + user + ", " + password;
        boolean insertData = databaseHelper.addData(patient);
        if (insertData){
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong");
        }
    }

}
