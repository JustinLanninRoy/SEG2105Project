package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class createEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
    }

    public void onCreatePatient(View view){

        TextView firstName = (TextView) findViewById(R.id.FirstName);
        String firstname = firstName.getText().toString().trim();

        TextView lastName = (TextView) findViewById(R.id.LastName);
        String lastname = lastName.getText().toString().trim();

        TextView superVisor = (TextView) findViewById(R.id.supervisor);
        String supervisor = superVisor.getText().toString().trim();

        TextView Position = (TextView) findViewById(R.id.position);
        String position = Position.getText().toString().trim();

        TextView Department = (TextView) findViewById(R.id.department);
        String department = Department.getText().toString().trim();

        TextView employeeNumber = (TextView) findViewById(R.id.employeeNumber);
        String employeenumber = employeeNumber.getText().toString().trim();

        TextView SSN = (TextView) findViewById(R.id.ssn);
        String ssn = SSN.getText().toString().trim();

        TextView Email = (TextView) findViewById(R.id.Email);
        String email = Email.getText().toString().trim();

        TextView Phone = (TextView) findViewById(R.id.phoneNumber);
        String phone = Phone.getText().toString().trim();

        TextView userName = (TextView) findViewById(R.id.username);
        String username = userName.getText().toString().trim();

        TextView userPassword = (TextView) findViewById(R.id.password);
        String userpassword = userPassword.getText().toString().trim();


        if(firstname.equalsIgnoreCase("") || lastname.equalsIgnoreCase("") || username.equalsIgnoreCase("") || userpassword.equalsIgnoreCase("") || employeenumber.equalsIgnoreCase("") || ssn.equalsIgnoreCase("") || email.equalsIgnoreCase("") || phone.equalsIgnoreCase("") ||supervisor.equalsIgnoreCase("") || department.equalsIgnoreCase("") || position.equalsIgnoreCase("")){
            Toast.makeText(getApplicationContext(),"Please fill in every category",Toast.LENGTH_LONG).show();
        } else {
            //creating the string
            String postLogginString = ("Welcome " + firstname + "! You are logged-in as an Employee");

            //opening the PostLoggin class and sending the message with it
            Intent i = new Intent(this, PostLoggin.class);
            i.putExtra("message", postLogginString);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
        }


    }
}
