package com.example.walkinclinic;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;



public class CreateEmployee extends CreatePerson {
    EditText employeeNum;
    EditText clinic;
    RadioGroup time;
    private static final String TAG = "CreateEmployee";

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        employeeNum = findViewById(R.id.employeeNumber);
        clinic = findViewById(R.id.clinic);
        time = findViewById(R.id.workTime);
        databaseHelper = new DatabaseHelper(this);
    }

    private  void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onCreatePerson(View view) {
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
        else if (checkEmpty(employeeNum)) {
            return true;
        }
        else if (checkEmpty(clinic)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    void openPostLoggin() {
        addEmployee();
        //creating the string
        String postLogginString = ("Welcome Employee " + firstName.getText().toString() + "! You are logged-in.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

    private void addEmployee() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String pos = clinic.getText().toString().trim();
        String eNum = employeeNum.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String user = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        int selected = time.getCheckedRadioButtonId();
        RadioButton chosen = findViewById(selected);
        String work = chosen.getText().toString().trim();
        String employee = fName + ", " + lName + ", " + eNum + ", " + pos + ", " + email + ", " + phone + ", " + user + ", " + password + ", " + work;
        boolean insertData = databaseHelper.addDataA(employee);
        if (insertData){
            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong");
        }
    }
}
