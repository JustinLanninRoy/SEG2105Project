package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public abstract class CreatePerson extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText firstName;
    EditText lastName;
    EditText Email;
    EditText Phone;
    EditText userName;
    EditText userPassword;

    public void onCreatePerson(View view){

        //Go get the values of all the fields that are variables
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        Email = findViewById(R.id.Email);
        Phone = findViewById(R.id.phoneNumber);
        userName = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);

        //check for all fields being entered
        if(checkAllFields()){
            Toast.makeText(getApplicationContext(),"Please fill in every category.",Toast.LENGTH_LONG).show();
        }
        else if(checkAlphabet(firstName) || checkAlphabet(lastName)){
            Toast.makeText(getApplicationContext(), "Please enter a valid name.", Toast.LENGTH_LONG).show();
        }
        //check email format
        else if(!checkEmailValid(Email)){
            Toast.makeText(getApplicationContext(), "Please enter a valid email.", Toast.LENGTH_LONG).show();
        }
        //check phone format
        else if(!checkPhoneValid(Phone)) {
            Toast.makeText(getApplicationContext(), "Please use only numbers and XXX-XXX-XXXX format.", Toast.LENGTH_LONG).show();
        }

        //open the postlogin class and display the correct message
        else {
            openPostLogin();
        }
    }

    void openPostLogin() {
        //creating the string
        String postLogginString = ("Welcome " + firstName.getText().toString() + "! You are logged-in.");
        //opening the PostLoggin class and sending the message with it
        Intent i = new Intent(this, PostLogin.class);
        i.putExtra("message", postLogginString);
        startActivity(i);
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
        if(!text.getText().toString().trim().replace("-", "").matches("[0-9]+") || text.getText().toString().trim().length() != 12){
            return false;
        }
        return true;
    }

    boolean checkAlphabet(EditText text){
        //check to make sure the field only contains letters
        if(!text.getText().toString().trim().matches("^[a-zA-Z]*$")){
            return true;
        }
        return false;
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
