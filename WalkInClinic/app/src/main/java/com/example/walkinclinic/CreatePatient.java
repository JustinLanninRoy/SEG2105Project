package com.example.walkinclinic;

import android.database.Cursor;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePatient extends CreatePerson {
    EditText a;
    EditText streetAddress;
    private static final String TAG = "CreatePatient";
    EditText userName;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        a = findViewById(R.id.Age);
        streetAddress = findViewById(R.id.StreetAddress);
        databaseHelper = new DatabaseHelper(this);
        userName = findViewById(R.id.username);
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onCreatePerson(View view){
        if (checkUsername()){
            toastMessage("This username already exists, please try a different one.");
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

    boolean checkUsername(){
        String user = userName.getText().toString().trim();
        Cursor data1 = databaseHelper.getEmployee(user);
        Cursor data2 = databaseHelper.getPatient(user);
        String exists = null;
        while (data1.moveToNext()){
            exists = data1.getString(6);
        }
        while (data2.moveToNext()){
            exists = data2.getString(7);
        }
        if (exists == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    void openPostLogin() {
        addPatient();
        //creating the string
        String postLogginString = ("Welcome Patient " + firstName.getText().toString() + "! You are logged-in.");
        String user = userName.getText().toString().trim();
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLogin.class);
        i.putExtra("message", postLogginString);
        i.putExtra("username", user);
        databaseHelper.close();
        startActivity(i);
        CreatePatient.this.finish();
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
        databaseHelper.addData(fName, lName, address, age, email, phone, user, password);
    }

}
