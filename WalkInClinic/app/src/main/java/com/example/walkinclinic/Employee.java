package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Employee extends Person {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
    }

    public void onCreatePerson(View view){
        super.onCreatePerson(view);
    }

    @Override
    void openPostLoggin() {
        //creating the string
        String postLogginString = ("Welcome " + firstName.getText().toString() + "! You are logged-in as an Employee.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLoggin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
        Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
    }

    boolean checkEmailValid(EditText text){
        return super.checkEmailValid(text);
    }

    boolean checkEmpty(EditText text){
        return super.checkEmpty(text);
    }

    boolean checkPhoneValid(EditText text){
        return super.checkPhoneValid(text);
    }

    boolean checkAlphabet(EditText text){
        return super.checkAlphabet(text);
    }

    //method to check if any field is left blank. Returns true if anything is checkEmpty = true, return false if everything is filled.
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
        else{
            return false;
        }
    }
}
