package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.lang.String;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
    }

    public void onLoggin(View view){

        //getting the username input
        TextView userName = (TextView) findViewById(R.id.txtEditUsername);
        String username = userName.getText().toString().trim();

        //getting the password input
        TextView passWord = (TextView) findViewById(R.id.txtEditPassword);
        String password = passWord.getText().toString().trim();

        int x = validateLogin(username, password);
        if(x == 1){
            String postLogginString = ("Welcome Admin! You are logged-in.");
            Intent i = new Intent(this, PostLogin.class);
            i.putExtra("message", postLogginString);
            startActivity(i);
            db.close();
        } else if (x == 2){
            String postLogginString = ("Welcome Patient " + name + "! You are logged-in.");
            Intent i = new Intent(this, PostLogin.class);
            i.putExtra("message", postLogginString);
            i.putExtra("username", username);
            startActivity(i);
            db.close();
        } else if (x == 3){
            String postLogginString = ("Welcome Employee " + name + "! You are logged-in.");
            Intent i = new Intent(this, PostLogin.class);
            i.putExtra("message", postLogginString);
            i.putExtra("username", username);
            startActivity(i);
            db.close();
        } else {
            Toast.makeText(getApplicationContext(),"Please enter your username & password correctly, or create a new account.",Toast.LENGTH_LONG).show();
        }
    }

    public int validateLogin(String username, String password){
        Cursor employees = db.getEmployee(username);
        Cursor patients = db.getPatient(username);
        String exists = null;
        String epassword = null;
        String ppassword = null;
        while (employees.moveToNext()){
            exists = employees.getString(6);
            epassword = employees.getString(7);
            name = employees.getString(1);
        }
        while (patients.moveToNext()){
            exists = patients.getString(7);
            ppassword = patients.getString(8);
            name = patients.getString(1);
        }
        return valid(exists, username, password, epassword, ppassword);
    }

    public int valid(String exists, String username, String password, String epassword, String ppassword){
        if (exists != null && password.equals(epassword)){
            return 3;
        } else if (exists != null && password.equals(ppassword)){
            return 2;
        } else if(username.equals("admin") && password.equals("5T5ptQ")){
            return 1;
        } else {
            return 0;
        }
    }

    public void onCreateAccount(View view){

        //getting the value of the radio button selected
        RadioGroup radioAccountTypeGroup = (RadioGroup) findViewById(R.id.AccountType);

        // get selected radio button from radioGroup
        int selectedId = radioAccountTypeGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton selectedRadioButtonAccount = (RadioButton) findViewById(selectedId);

        String type = selectedRadioButtonAccount.getText().toString().trim();

        //selecting the correct window to open
        if(type.equalsIgnoreCase("Patient")){
            Intent i = new Intent(this, CreatePatient.class);
            startActivity(i);
        }
        if(type.equalsIgnoreCase( "Employee")){
            Intent i = new Intent(this, CreateEmployee.class);
            startActivity(i);
        }
    }
}