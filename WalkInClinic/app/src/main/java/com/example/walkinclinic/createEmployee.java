package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

public class createEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
    }

    EditText firstName;
    EditText lastName;
    EditText superVisor;
    EditText Position;
    EditText Department;
    EditText employeeNumber;
    EditText SSN;
    EditText Email;
    EditText Phone;
    EditText userName;
    EditText userPassword;


    public void onCreatePatient(View view){

         firstName = findViewById(R.id.FirstName);
         lastName = findViewById(R.id.LastName);
         superVisor = findViewById(R.id.supervisor);
         Position = findViewById(R.id.position);
         Department = findViewById(R.id.department);
         employeeNumber = findViewById(R.id.employeeNumber);
         SSN = findViewById(R.id.ssn);
         Email = findViewById(R.id.Email);
         Phone = findViewById(R.id.phoneNumber);
         userName = findViewById(R.id.username);
         userPassword = findViewById(R.id.password);



        if(checkAllFields()){
            Toast.makeText(getApplicationContext(),"Please fill in every category.",Toast.LENGTH_LONG).show();
        }
        else if(checkAlphabet(firstName) || checkAlphabet(lastName)){
            Toast.makeText(getApplicationContext(), "Please enter a valid name.", Toast.LENGTH_LONG).show();
        }
        else if(!checkEmailValid(Email)){
            Toast.makeText(getApplicationContext(),"Please enter valid email.",Toast.LENGTH_LONG).show();
        }
        else if(!checkPhoneValid(Phone)){
            Toast.makeText(getApplicationContext(),"Please enter valid phone number.",Toast.LENGTH_LONG).show();
        }
        else {
            //creating the string
            String postLogginString = ("Welcome " + firstName.getText().toString().trim() + "! You are logged-in as an Employee");

            //opening the PostLoggin class and sending the message with it
            Intent i = new Intent(this, PostLoggin.class);
            i.putExtra("message", postLogginString);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
        }

    }

    boolean checkEmailValid(EditText text){
        //getting email as a CharSequence because patterns matcher takes a CharSequence
        CharSequence emailString = text.getText().toString().trim();
        //checking that the email isn't null and that the email matches the PATTERN of an email
        //this returns true if the email field has something written in it and it looks like an email
        return(Patterns.EMAIL_ADDRESS.matcher(emailString).matches());
    }

    boolean checkEmpty(EditText text){
        //get the inside of the text field being checked
        CharSequence s = text.getText().toString();
        //returns true if the text field is just empty
        return TextUtils.isEmpty(s);
    }

    boolean checkPhoneValid(EditText text){
        //check that the phone number is only made up of number when the dash is removed.
        if(!text.getText().toString().trim().replace("-", "").matches("^[0-9]*$")){
            Toast.makeText(getApplicationContext(), "Please use only numbers.", Toast.LENGTH_LONG).show();
            return true;
        }
        //make sure the phone number is input in the correct format
        else if(text.getText().toString().trim().length() > 12){
            Toast.makeText(getApplicationContext(), "Please use XXX-XXX-XXXX format.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    boolean checkAlphabet(EditText text){
        //check to make sure the field only contains letters
        if(!text.getText().toString().trim().matches("^[a-zA-Z]*$")){
            Toast.makeText(getApplicationContext(), "Use only letters for name fields.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    boolean checkAllFields(){
        if(checkEmpty(firstName)){
            return true;
        }
        else if(checkEmpty(lastName)){
            return true;
        }
        else if (checkEmpty(superVisor)) {
            return true;
        }
        else if (checkEmpty(Position)){
            return true;
        }
        else if (checkEmpty(Department)){
            return true;
        }
        else if(checkEmpty(employeeNumber)){
            return true;
        }
        else if(checkEmpty(SSN)){
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
