package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
    }

    public void onCreatePatient(View view){

        TextView firstName = (TextView) findViewById(R.id.FirstName);
        String firstname = firstName.getText().toString().trim();

        TextView lastName = (TextView) findViewById(R.id.LastName);
        String lastname = lastName.getText().toString().trim();

        TextView streetAddress = (TextView) findViewById(R.id.StreetAddress);
        String streetaddress = streetAddress.getText().toString().trim();

        TextView postalCode = (TextView) findViewById(R.id.postalCode);
        String postalcode = postalCode.getText().toString().trim();

        TextView Age = (TextView) findViewById(R.id.Age);
        String age = Age.getText().toString().trim();

        TextView Email = (TextView) findViewById(R.id.Email);
        String email = Email.getText().toString().trim();

        TextView Phone = (TextView) findViewById(R.id.phoneNumber);
        String phone = Phone.getText().toString().trim();

        TextView userName = (TextView) findViewById(R.id.username);
        String username = userName.getText().toString().trim();

        TextView userPassword = (TextView) findViewById(R.id.password);
        String userpassword = userPassword.getText().toString().trim();


        if(firstname.equalsIgnoreCase("") || lastname.equalsIgnoreCase("") || username.equalsIgnoreCase("") || userpassword.equalsIgnoreCase("") || streetaddress.equalsIgnoreCase("") || postalcode.equalsIgnoreCase("") || email.equalsIgnoreCase("") || phone.equalsIgnoreCase("") ||age.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(),"Please fill in every category",Toast.LENGTH_LONG).show();
        } else {
            //creating the string
            String postLogginString = ("Welcome " + firstname + "! You are logged-in as a patient");

            //opening the PostLoggin class and sending the message with it
            Intent i = new Intent(this, PostLoggin.class);
            i.putExtra("message", postLogginString);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
        }


    }
}
